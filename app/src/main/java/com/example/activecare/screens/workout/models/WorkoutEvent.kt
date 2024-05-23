package com.example.activecare.screens.workout.models

import org.osmdroid.views.MapView

sealed class WorkoutEvent {
    data object StreetRun : WorkoutEvent()
    data object TrackRun : WorkoutEvent()
    data object Walking : WorkoutEvent()
    data object Bike : WorkoutEvent()
    data object BackClicked : WorkoutEvent()
    data class StartWorkout(val value: MapView) : WorkoutEvent()
    data class TimeStartChanged(val value: String) : WorkoutEvent()
    data class TimeEndChanged(val value: String) : WorkoutEvent()
    data class CaloriesChanged(val value: String) : WorkoutEvent()
    data class DistanceChanged(val value: String) : WorkoutEvent()
    data object OnButtonClicked : WorkoutEvent()
    data object PauseWorkout : WorkoutEvent()
    data class ContinueWorkout(val value: MapView) : WorkoutEvent()
    data object SendData : WorkoutEvent()
    data object ChangeViewOnWorkoutStarted : WorkoutEvent()
    data object ErrorShown : WorkoutEvent()
}