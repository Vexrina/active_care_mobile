package com.example.activecare.screens.person.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activecare.common.EventHandler
import com.example.activecare.common.cache.domain.Cache
import com.example.activecare.common.dataclasses.Limitation
import com.example.activecare.network.domain.ApiService
import com.example.activecare.screens.person.domain.BluetoothController
import com.example.activecare.screens.person.domain.BluetoothDeviceDomain
import com.example.activecare.screens.person.models.BluetoothViewState
import com.example.activecare.screens.person.models.PersonEvent
import com.example.activecare.screens.person.models.PersonSubState
import com.example.activecare.screens.person.models.PersonViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val apiService: ApiService,
    private val bluetoothController: BluetoothController,
    private val cache: Cache,
) : ViewModel(), EventHandler<PersonEvent> {
    private val _viewState: MutableStateFlow<PersonViewState> = MutableStateFlow(PersonViewState())
    val viewState: StateFlow<PersonViewState> = _viewState

    private val _bluetoothState = MutableStateFlow(BluetoothViewState())
    val bluetoothState = combine(
        bluetoothController.scannedDevices,
        bluetoothController.pairedDevices,
        _bluetoothState
    ) { scannedDevices, pairedDevices, state ->
        state.copy(
            scannedDevices = scannedDevices,
            pairedDevices = pairedDevices
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _bluetoothState.value
    )

    override fun obtainEvent(event: PersonEvent) {
        when (event) {
            PersonEvent.BackClicked -> backSubState()
            PersonEvent.DevicesClicked -> changeSubState(PersonSubState.Devices)
            PersonEvent.NotificationsClicked -> changeSubState(PersonSubState.NotificationSettings)
            PersonEvent.ProfileClicked -> changeSubState(PersonSubState.ProfileSetting)
            PersonEvent.RecommendationsClicked -> TODO()
            PersonEvent.SettingsClicked -> changeSubState(PersonSubState.Settings)
            PersonEvent.StatClicked -> changeSubState(PersonSubState.Stat)
            PersonEvent.WorkoutClicked -> changeSubState(PersonSubState.Workouts)
            PersonEvent.DevicesSettingsClicked -> changeSubState(PersonSubState.DeviceSettings)
            PersonEvent.BluetoothStartScanClicked -> startScan()
            PersonEvent.BluetoothStopScanClicked -> stopScan()
            is PersonEvent.DateChanged -> dateChanged(event.value)
            is PersonEvent.LoadData -> loadData(event.value)
            is PersonEvent.BluetoothDeviceClicked -> connectToDevice(event.value)
            PersonEvent.onLogOutClicked -> logOut()
        }
    }

    private fun logOut() {
        cache.userSignOut()
    }

    private fun backSubState() {
        if (_viewState.value.personSubState in listOf(
                PersonSubState.DeviceSettings,
                PersonSubState.NotificationSettings,
                PersonSubState.ProfileSetting
            )
        ) {
            changeSubState(PersonSubState.Settings)
        } else {
            if (_viewState.value.personSubState in listOf(
                    PersonSubState.Stat,
                    PersonSubState.Workouts,
                )
            ) {
                _viewState.update {
                    it.copy(
                        selectedDate = ""
                    )
                }
            }
            changeSubState(PersonSubState.Default)
        }
    }

    private fun changeSubState(newSubState: PersonSubState) {
        _viewState.update {
            it.copy(
                personSubState = newSubState
            )
        }
    }

    private fun startScan() {
        bluetoothController.startDiscovery()
    }

    private fun stopScan() {
        bluetoothController.stopDiscovery()
    }

    private fun connectToDevice(device: BluetoothDeviceDomain) {
        bluetoothController.connectToDevice(device)
    }

    private fun dateChanged(value: String) {
        _viewState.update {
            it.copy(
                selectedDate = value
            )
        }
    }

    private fun loadData(limit: Limitation) {
        Log.d("PVM", "HERE1")
        when (_viewState.value.personSubState) {
            PersonSubState.Stat -> {
                loadStatData(limit)
            }

            PersonSubState.Workouts -> {
                loadWorkoutData(limit)
            }

            PersonSubState.Default -> {
                loadUserName()
            }

            else -> {}
        }
    }

    private fun loadUserName() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update {
                it.copy(
                    isLoad = true
                )
            }
            val response = apiService.getUser()
            if (response.second != null) Log.d("PVM", response.second!!.message!!)
            _viewState.update {
                it.copy(
                    isLoad = false,
                    user = response.first
                )
            }
        }
    }

    private fun loadStatData(limit: Limitation) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update {
                it.copy(
                    isLoad = true,
                    limit = Limitation(
                        limit.date,
                        date_offset = 0
                    ),
                )
            }

            val response = apiService.getStatActivityAndMeasure(
                _viewState.value.selectedDate.substring(0, 10)
            )

            if (response.second != null) {
                Log.d("PVM", response.second!!.message!!)
            }

            _viewState.update {
                it.copy(
                    stats = Pair(response.first.activity, response.first.measure),
                    isLoad = false,
                )
            }
            Log.d("pvm", _viewState.value.stats.toString())
        }
    }

    private fun loadWorkoutData(limit: Limitation) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update {
                it.copy(
                    isLoad = true,
                    limit = limit,
                )
            }
            val response = apiService.getWorkoutActivityAndMeasure(
                _viewState.value.selectedDate.substring(0, 10)
            )

            if (response.second != null) {
                Log.d("PVM", response.second!!.message!!)
            }

            _viewState.update {
                it.copy(
                    workout = Pair(response.first.activity, response.first.measure),
                    isLoad = false,
                )
            }
        }
    }
}