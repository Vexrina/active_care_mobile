package com.example.activecare.screens.workout.ui

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activecare.ActiveCareApplication
import com.example.activecare.common.EventHandler
import com.example.activecare.common.calculateBurnedCalories
import com.example.activecare.common.millisToDateStamp
import com.example.activecare.common.dataclasses.TimeStamp
import com.example.activecare.common.dataclasses.Workout
import com.example.activecare.network.domain.ApiService
import com.example.activecare.screens.workout.data.LocationHelper
import com.example.activecare.screens.workout.models.WorkoutEvent
import com.example.activecare.screens.workout.models.WorkoutSubState
import com.example.activecare.screens.workout.models.WorkoutViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val apiService: ApiService,
) : ViewModel(), EventHandler<WorkoutEvent> {

    private val _viewState: MutableStateFlow<WorkoutViewState> = MutableStateFlow(WorkoutViewState())
    val viewState: StateFlow<WorkoutViewState> = _viewState

    private val locationHelper = LocationHelper(ActiveCareApplication.instance)

    private val _locations = mutableListOf<GeoPoint>()

    init {
        locationHelper.getCurrentLocation{ location ->
            _viewState.update {
                it.copy(
                    currentLocation = location
                )
            }
            _locations.add(GeoPoint(
                _viewState.value.currentLocation!!.latitude,
                _viewState.value.currentLocation!!.longitude,
            ))
        }
    }

    override fun obtainEvent(event: WorkoutEvent) {
        when (event){
            WorkoutEvent.Bike -> changeSubState(WorkoutSubState.Bike)
            WorkoutEvent.StreetRun -> changeSubState(WorkoutSubState.StreetRun)
            WorkoutEvent.TrackRun -> changeSubState(WorkoutSubState.TrackRun)
            WorkoutEvent.Walking -> changeSubState(WorkoutSubState.Walking)
            WorkoutEvent.BackClicked -> changeSubState(WorkoutSubState.Default)
            is WorkoutEvent.StartWorkout -> startWorkout(event.value)
            is WorkoutEvent.CaloriesChanged -> changedCalories(event.value)
            is WorkoutEvent.DistanceChanged -> changedDistance(event.value)
            is WorkoutEvent.TimeEndChanged -> changedTimeEnd(event.value)
            is WorkoutEvent.TimeStartChanged -> changedTimeStart(event.value)
            WorkoutEvent.PauseWorkout -> pauseWorkout()
            WorkoutEvent.ChangeViewOnWorkoutStarted -> changeViewOnWorkout()
            is WorkoutEvent.ContinueWorkout -> unpauseWorkout(event.value)
            WorkoutEvent.SendData -> sendData()
        }
    }

    private var pausedLocation: Location? = null
    private var pauseStartTime: Long? = null
    private var totalDistanceOnPause: Float = 0.0f
    private var isPaused = false

    private fun changeViewOnWorkout(){
        _viewState.update {
            it.copy(
                isWorkout = true
            )
        }
    }

    private fun startWorkout(mapView: MapView) {
        if(!isPaused){
            if (_viewState.value.startedTime == null) {
                val startTime = System.currentTimeMillis()
                _viewState.update {
                    it.copy(
                        startedTime = startTime,
                    )
                }
            }

            if (pausedLocation != null && pauseStartTime != null) {
                // Если была приостановка, вычисляем время и местоположение с начала приостановки
                locationHelper.getCurrentLocation(mapView) { location ->
                    val pauseDuration = System.currentTimeMillis() - pauseStartTime!! + _viewState.value.summaryPauseDuration
                    val pauseDistance = pausedLocation!!.distanceTo(location!!) + _viewState.value.summaryPauseDistance

                    _viewState.update {
                        it.copy(
                            summaryPauseDuration = pauseDuration,
                            summaryPauseDistance = pauseDistance,
                        )
                    }
                    pausedLocation = null
                    pauseStartTime = null

                    val endTime =  calculateTime()
                    handleNewLocation(location, endTime, true)
                }
            } else {
                // Если это первый запуск или приложение не было приостановлено, обработать текущее местоположение
                locationHelper.getCurrentLocation(mapView) { location ->
                    val endTime = calculateTime()
                    handleNewLocation(location!!, endTime)
                }
            }
        }
    }
    private fun calculateTime(): Long{
        return System.currentTimeMillis() - _viewState.value.startedTime!! - _viewState.value.summaryPauseDuration
    }

    private fun handleNewLocation(
        location: Location,
        endTime: Long,
        wasPause: Boolean = false
    ) {
        val newDistance = if (_viewState.value.currentLocation != null && !wasPause) {
            _viewState.value.currentLocation!!.distanceTo(location)
        } else if (wasPause) {
            val temp = _viewState.value.currentLocation!!.distanceTo(location) - _viewState.value.summaryPauseDistance
            if (temp>0){
                temp
            } else
                0.0f
        } else {
            0.0f
        }


        val newTimeStamp = TimeStamp(
            hours = (endTime / (1000 * 60 * 60)).toInt(),
            minutes = (endTime / (1000 * 60)).toInt() % 60,
            seconds = (endTime / 1000).toInt() % 60,
            millis = endTime,
        )

        val totalDistance = if (!isPaused) {
            _viewState.value.distance + newDistance
        }  else{
            _viewState.value.distance
        }

        _viewState.update {
            it.copy(
                currentLocation = location,
                distance = totalDistance,
                endTime = newTimeStamp
            )
        }

        _locations.add(GeoPoint(location.latitude, location.longitude))
    }

    private fun pauseWorkout() {
        // Сохраняем информацию о местоположении и времени приостановки
        pausedLocation = _viewState.value.currentLocation
        pauseStartTime = System.currentTimeMillis()
        totalDistanceOnPause = _viewState.value.distance
        isPaused = true
    }

    private fun unpauseWorkout(mapView: MapView) {
        // Сохраняем информацию о местоположении и времени приостановки
        isPaused = false
        startWorkout(mapView)
    }

    private fun changeSubState(newSubState: WorkoutSubState) {
        _viewState.update {
            it.copy(
                workoutSubState = newSubState,
                isWorkout = false,
            )
        }
    }

    private fun changedCalories(value: String){
        _viewState.update {
            it.copy(
                trackCalories = value
            )
        }
    }
    private fun changedDistance(value: String){
        _viewState.update {
            it.copy(
                trackDistance = value
            )
        }
    }
    private fun changedTimeEnd(value: String){
        _viewState.update {
            it.copy(
                trackEndTime = value
            )
        }
    }
    private fun changedTimeStart(value: String){
        _viewState.update {
            it.copy(
                trackStartTime = value
            )
        }
    }

    private fun sendData(){
        pauseWorkout()
        viewModelScope.launch(Dispatchers.IO) {
            val workoutType: Int = when(_viewState.value.workoutSubState){
                WorkoutSubState.Bike -> 3
                WorkoutSubState.Walking-> 2
                WorkoutSubState.StreetRun -> 1
                WorkoutSubState.TrackRun -> 0
                WorkoutSubState.Default -> 5
                WorkoutSubState.WorkoutProccesing -> 5
            }
            val weightError = apiService.getUserWeight()
            if (weightError.second!=null){
                Log.d("WVM", "${weightError.second}")
                return@launch
            }
            val distance = _viewState.value.distance
            val endTime = _viewState.value.endTime.millis/1000
            val burnedCalories = calculateBurnedCalories(
                seconds = endTime.toInt(),
                distance = distance,
                workoutType = workoutType,
                weight = weightError.first!!
            )
            val newWorkoutRecord = Workout(
                time_start = millisToDateStamp(_viewState.value.startedTime!!),
                time_end = millisToDateStamp(pauseStartTime!!),
                burned_calories = burnedCalories.toInt(),
                avg_pulse = 0f,
                distance = distance,
                workout_type = workoutType,
            )

            val workoutError = apiService.appendUserData(newWorkoutRecord)
            if(workoutError.second != null){
                Log.d("WVM", workoutError.second!!.message!!)
                return@launch
            }
            changeSubState(WorkoutSubState.Default)
        }
    }
}