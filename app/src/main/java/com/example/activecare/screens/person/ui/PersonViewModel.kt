package com.example.activecare.screens.person.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activecare.common.EventHandler
import com.example.activecare.network.domain.ApiService
import com.example.activecare.screens.person.domain.BluetoothController
import com.example.activecare.screens.person.models.BluetoothViewState
import com.example.activecare.screens.person.models.PersonEvent
import com.example.activecare.screens.person.models.PersonSubState
import com.example.activecare.screens.person.models.PersonViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    apiService: ApiService,
    private val bluetoothController: BluetoothController,
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
        }
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
}