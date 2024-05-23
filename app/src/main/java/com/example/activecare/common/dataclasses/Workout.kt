package com.example.activecare.common.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class Workout(
    val time_start: String,
    val time_end: String,
    val avg_pulse: Float,
    val burned_calories: Int,
    val distance: Float,
    val workout_type: Int,
)
