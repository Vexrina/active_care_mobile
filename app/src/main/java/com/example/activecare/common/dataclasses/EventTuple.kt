package com.example.activecare.common.dataclasses

import com.example.activecare.screens.home.models.HomeEvent
import com.example.activecare.screens.signin.models.SignInEvent
import com.example.activecare.screens.workout.models.WorkoutEvent

data class EventTuple(
    val SignInEvent: SignInEvent?= null,
    val HomeEvent: HomeEvent? = null,
    val WorkoutEvent: WorkoutEvent? = null,
    val Message: String,
)
