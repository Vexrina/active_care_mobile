package com.example.activecare.screens.workout.models

import android.location.Location
import com.example.activecare.common.dataclasses.EventTuple
import com.example.activecare.common.dataclasses.TimeStamp
import com.example.activecare.common.dataclasses.User
import kotlinx.coroutines.channels.Channel
import org.osmdroid.util.GeoPoint

data class WorkoutViewState(
    val workoutSubState: WorkoutSubState = WorkoutSubState.Default,
    val user: User? = User(),
    val currentLocation: Location? = null,
    val locations: List<GeoPoint> = emptyList(),
    val distance: Float = 0f,
    val startedTime: Long? = null,
    val endTime: TimeStamp =TimeStamp(),

    val trackStartTime: String ="",
    val trackEndTime: String ="",
    val trackCalories: String ="",
    val trackDistance: String ="",
    val isWorkout: Boolean = false,

    val summaryPauseDuration: Long = 0,
    val summaryPauseDistance: Float = 0f,

    val eventChannel: Channel<EventTuple> = Channel(Channel.BUFFERED),
    )
