package com.example.activecare.screens.home.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activecare.common.EventHandler
import com.example.activecare.common.cache.domain.Cache
import com.example.activecare.common.dataclasses.EventTuple
import com.example.activecare.common.dataclasses.FoodRecord
import com.example.activecare.common.dataclasses.Limitation
import com.example.activecare.common.dataclasses.Stat
import com.example.activecare.common.filterByDate
import com.example.activecare.common.getCurrentDate
import com.example.activecare.common.getPrevDate
import com.example.activecare.common.simpleDateTimeParser
import com.example.activecare.common.simpleStringParser
import com.example.activecare.network.domain.ApiService
import com.example.activecare.screens.home.models.AddCaloriesState
import com.example.activecare.screens.home.models.HomeEvent
import com.example.activecare.screens.home.models.HomeSubState
import com.example.activecare.screens.home.models.HomeViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

@HiltViewModel(assistedFactory = HomeViewModel.HomeViewModelFactory::class)
class HomeViewModel @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted private val apiService: ApiService,
    @Assisted private val cache: Cache,
) : ViewModel(), EventHandler<HomeEvent>, SensorEventListener {
    @AssistedFactory
    interface HomeViewModelFactory {
        fun create(
            context: Context,
            apiService: ApiService,
            cache: Cache,
        ): HomeViewModel
    }

    private val _viewState: MutableStateFlow<HomeViewState> = MutableStateFlow(HomeViewState())
    val viewState: StateFlow<HomeViewState> = _viewState

    private val _addCaloriesState: MutableStateFlow<AddCaloriesState> = MutableStateFlow(
        AddCaloriesState()
    )
    val addCaloriesState: StateFlow<AddCaloriesState> = _addCaloriesState

    override fun obtainEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.CaloriesClicked -> changeSubState(HomeSubState.Calories)
            HomeEvent.PulseClicked -> changeSubState(HomeSubState.Pulse)
            HomeEvent.SleepClicked -> changeSubState(HomeSubState.Sleep)
            HomeEvent.SpO2Clicked -> changeSubState(HomeSubState.Sp02)
            HomeEvent.WaterClicked -> changeSubState(HomeSubState.Water)
            HomeEvent.WeightClicked -> changeSubState(HomeSubState.Weight)
            HomeEvent.BackClicked -> backClicked()
            is HomeEvent.AddFoodRecordClicked -> addFoodRecord(event.value)
            is HomeEvent.CaloriesChanged -> caloriesChanged(event.value)
            is HomeEvent.CarbohydratesChanged -> carbohydratesChanged(event.value)
            is HomeEvent.FatsChanged -> fatsChanged(event.value)
            is HomeEvent.FoodNameChanged -> foodNameChanged(event.value)
            is HomeEvent.ProteinsChanged -> proteinsChanged(event.value)
            is HomeEvent.NewWeightChanged -> weightChanged(event.value)
            is HomeEvent.LoadData -> loadData(event.value)
            is HomeEvent.DateChanged -> dateChanged(event.value)
            HomeEvent.AddWeight -> sendNewWeight()
            is HomeEvent.ChangeWater -> sendWater(event.value)
            is HomeEvent.SleepChanged -> sleepChanged(event.value)
            HomeEvent.SleepSend -> sendSleep()
            else -> {}
        }
    }

    private fun loadData(limit: Limitation) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update {
                it.copy(
                    isLoad = true,
                    limit = limit
                )
            }
            val stats = apiService.getUserStat(limit)
            if (stats.second != null) {
                Log.d("HVM", "${stats.second!!.message}")
            }
            val foodRecords = apiService.getUserFoodRecords(limit)
            if (foodRecords.second != null) {
                Log.d("HVM", "${foodRecords.second!!.message}")
            }
            _viewState.update {
                val newStats = it.stats + stats.first
                val newRecords = it.foodRecord + foodRecords.first
                it.copy(
                    isLoad = false,
                    stats = newStats.sortedByDescending { it.date_stamp },
                    foodRecord = newRecords.sortedByDescending { it.date_stamp },
                )
            }
            Log.d("HVM", _viewState.value.stats.toString())
        }
    }

    private fun backClicked() {
        when (_viewState.value.homeSubState) {
            HomeSubState.AddCalories -> changeSubState(HomeSubState.Calories)
            else -> changeSubState(HomeSubState.Default)
        }
    }

    private fun changeSubState(newSubState: HomeSubState) {
        _viewState.update {
            it.copy(
                homeSubState = newSubState
            )
        }
    }

    private fun addFoodRecord(value: String) {
        if (_viewState.value.homeSubState != HomeSubState.AddCalories) {
            _viewState.update {
                it.copy(
                    homeSubState = HomeSubState.AddCalories,
                )
            }
            _addCaloriesState.update {
                it.copy(
                    foodType = value
                )
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    _viewState.update {
                        it.copy(
                            isLoad = true
                        )
                    }
                    checkNullable(HomeSubState.AddCalories)
                    val foodRecord = FoodRecord(
                        calories = _addCaloriesState.value.calories.toInt(),
                        carbohydrates = _addCaloriesState.value.carbohydrates.toInt(),
                        fats = _addCaloriesState.value.fats.toInt(),
                        foodname = _addCaloriesState.value.foodName,
                        proteins = _addCaloriesState.value.proteins.toInt(),
                        foodtype = when (_addCaloriesState.value.foodType) {
                            "Завтрак" -> 0
                            "Обед" -> 1
                            "Ужин" -> 2
                            "Перекус" -> 3
                            else -> 3
                        },
                        date_stamp = _viewState.value.selectedDate,
                    )
                    val result = apiService.appendUserData(foodRecord)
                    if (result.second != null) {
                        sendErrorEvent(result.second!!.message)
                        return@launch
                    }
                    _viewState.update {
                        it.copy(
                            isLoad = false
                        )
                    }
                    sendSuccessEvent()
                    loadData(Limitation(_viewState.value.selectedDate))
                    backClicked()
                } catch (ex: Exception) {
                    sendErrorEvent(ex.message)
                }
            }
        }
    }

    private fun checkNullable(subState: HomeSubState) {
        when (subState) {
            HomeSubState.AddCalories -> if (_addCaloriesState.value.calories == "" ||
                _addCaloriesState.value.carbohydrates == "" ||
                _addCaloriesState.value.fats == "" ||
                _addCaloriesState.value.proteins == "" ||
                _addCaloriesState.value.foodName == ""
            ) {
                throw NullPointerException("Some fields is empty")
            }

            HomeSubState.Weight -> if (_viewState.value.newWeight == "") {
                throw NullPointerException("Some fields is empty")
            }

            else -> return
        }
    }

    private fun sendErrorEvent(errorMessage: String?) {
        if (errorMessage != null) {
            viewModelScope.launch {
                _viewState.value.eventChannel.send(
                    EventTuple(
                        HomeEvent = HomeEvent.ErrorShown,
                        Message = errorMessage,
                    )
                )
            }
        }
    }

    private fun sendSuccessEvent() {
        viewModelScope.launch {
            _viewState.value.eventChannel.send(
                EventTuple(
                    HomeEvent = HomeEvent.BackClicked,
                    Message = "",
                )
            )
        }
    }

    private fun dateChanged(value: String) {
        _viewState.update {
            it.copy(
                selectedDate = value
            )
        }
    }

    private fun caloriesChanged(value: String) {
        _addCaloriesState.update {
            it.copy(
                calories = value
            )
        }
    }

    private fun carbohydratesChanged(value: String) {
        _addCaloriesState.update {
            it.copy(
                carbohydrates = value
            )
        }
    }

    private fun fatsChanged(value: String) {
        _addCaloriesState.update {
            it.copy(
                fats = value
            )
        }
    }

    private fun foodNameChanged(value: String) {
        _addCaloriesState.update {
            it.copy(
                foodName = value
            )
        }
    }

    private fun proteinsChanged(value: String) {
        _addCaloriesState.update {
            it.copy(
                proteins = value
            )
        }
    }

    private fun weightChanged(value: String) {
        _viewState.update {
            it.copy(
                newWeight = value
            )
        }
    }

    private fun sleepChanged(value: String) {
        _viewState.update {
            it.copy(
                sleep = value.replace('.', ':')
            )
        }
    }

    private fun sendSleep() {
        val sleepValue = _viewState.value.sleep
        if (!isValidTime(sleepValue)) {
            sendErrorEvent("Not valid time")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val selectedDate = simpleDateTimeParser(Calendar.getInstance())
            _viewState.update {
                it.copy(
                    isLoad = true,
                    selectedDate = selectedDate
                )
            }
            val oldStats = if (_viewState.value.stats.isNotEmpty()) _viewState.value.stats[0]
            else Stat("", 0f, 0, 0f, 0, 0f, 0)
            val sleepInt = (sleepValue.replace(':', '.').toFloat() * 100).toInt()
            val (_, error) = apiService.appendUserData(
                Stat(
                    sleep = sleepInt,
                    date_stamp = selectedDate,
                    pulse = oldStats.pulse,
                    steps = _stepCount.value,
                    oxygen_blood = oldStats.pulse,
                    weight = oldStats.weight,
                    water = oldStats.water,
                )
            )
            if (error != null) {
                sendErrorEvent(error.message)
                return@launch
            }
            loadData(Limitation(_viewState.value.selectedDate, deltatype = "week", date_offset = 1))
        }
    }

    private fun isValidTime(time: String): Boolean {
        val timeRegex = Regex("^[0-2]{0,1}[0-9]{1}:[0-5]{1}[0-9]{1}\$")
        return timeRegex.matches(time)
    }

    private fun sendNewWeight() {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update {
                it.copy(
                    isLoad = true
                )
            }
            checkNullable(HomeSubState.Weight)
            val filteredStats = filterByDate(
                stats = _viewState.value.stats,
                currentDate = simpleStringParser(_viewState.value.selectedDate)
            )
            try {
                val steps = if (_viewState.value.selectedDate.startsWith(getCurrentDate())) {
                    _stepCount.value
                } else if (filteredStats.isEmpty())
                    _viewState.value.stats[0].steps
                else 0
                val newStat = if (filteredStats.isEmpty()) {
                    Stat(
                        date_stamp = _viewState.value.selectedDate,
                        pulse = 0f,
                        steps = steps,
                        oxygen_blood = 0f,
                        sleep = 0,
                        weight = _viewState.value.newWeight.toFloat(),
                        water = 0,
                    )
                } else {
                    Stat(
                        date_stamp = _viewState.value.selectedDate,
                        pulse = _viewState.value.stats[0].pulse,
                        steps = steps,
                        oxygen_blood = _viewState.value.stats[0].oxygen_blood,
                        sleep = _viewState.value.stats[0].sleep,
                        weight = _viewState.value.newWeight.toFloat(),
                        water = _viewState.value.stats[0].water,
                    )
                }
                val result = apiService.appendUserData(newStat)
                if (result.second != null) {
                    sendErrorEvent(result.second!!.message)
                    return@launch
                }
                sendSuccessEvent()
                loadData(Limitation(_viewState.value.selectedDate))
            } catch (ex: Exception) {
                sendErrorEvent(ex.message)
            }
            Log.d("HVM", _viewState.value.stats[0].toString())
        }
    }

    private fun sendWater(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.update {
                it.copy(
                    isLoad = true
                )
            }
            val filteredStats = filterByDate(
                stats = _viewState.value.stats,
                currentDate = simpleStringParser(_viewState.value.selectedDate)
            )
            try {
                val steps = if (_viewState.value.selectedDate.startsWith(getCurrentDate())) {
                    _stepCount.value
                } else if (filteredStats.isEmpty())
                    _viewState.value.stats[0].steps
                else 0
                val newStat = if (filteredStats.isEmpty()) {
                    Stat(
                        date_stamp = _viewState.value.selectedDate,
                        pulse = 0f,
                        steps = steps,
                        oxygen_blood = 0f,
                        sleep = 0,
                        weight = _viewState.value.stats[0].weight,
                        water = 1,
                    )
                } else {
                    Stat(
                        date_stamp = _viewState.value.selectedDate,
                        pulse = _viewState.value.stats[0].pulse,
                        steps = steps,
                        oxygen_blood = _viewState.value.stats[0].oxygen_blood,
                        sleep = _viewState.value.stats[0].sleep,
                        weight = _viewState.value.stats[0].weight,
                        water = _viewState.value.stats[0].water + value,
                    )
                }
                val result = apiService.appendUserData(newStat)
                if (result.second != null) {
                    sendErrorEvent(result.second!!.message)
                    return@launch
                }
                sendSuccessEvent()
                loadData(Limitation(_viewState.value.selectedDate))
            } catch (ex: Exception) {
                sendErrorEvent(ex.message)
            }
            Log.d("HVM", _viewState.value.stats[0].toString())
        }
    }

    /**
     * Below is the code for working with the pedometer built into the phone.
     */

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    private val _totalSteps = MutableStateFlow(cache.getTotalSteps())
    private val lastUpdateCache = cache.getLastUpdate()

    private val badUpdate = (lastUpdateCache == null) || (lastUpdateCache == getPrevDate())

    private val todaySteps = if (lastUpdateCache == getCurrentDate()) cache.getTodaySteps() else 0
    private val _stepCount = MutableStateFlow(todaySteps)
    val stepCount: StateFlow<Int> = _stepCount

    fun startStepCounting() {
        stepSensor?.also { step ->
            sensorManager.registerListener(this, step, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
            if (badUpdate || _totalSteps.value == 0) {
                _totalSteps.value = event.values[0].toInt()
                cache.setTotalSteps(_totalSteps.value, getCurrentDate())
            }
            _stepCount.value = event.values[0].toInt() - _totalSteps.value
            cache.setTodaySteps(_stepCount.value)
            Log.d("HVM", "HERE")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
}