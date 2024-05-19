package com.example.activecare.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class Workout(
    val id: String? = null,
    val userid: String,
    val time_start: Int,
    val time_end: Int,
    val avg_pulse: Int,
    val burned_calories: Int,
)
