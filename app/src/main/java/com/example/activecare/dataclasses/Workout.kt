package com.example.activecare.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class Workout(
    val time_start: Int,
    val time_end: Int,
    val avg_pulse: Float,
    val burned_calories: Int,
    val distance: Float,
    val workout_type: Int,
)
