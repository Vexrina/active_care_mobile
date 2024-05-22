package com.example.activecare.dataclasses

data class ActivityWorkout(
    val date_stamp: String = "14 may",
    val totalDistance: Float = 6.32f,
    val streetRun: Float = 0.41f,
    val trackRun: Float = 0.0f,
    val walking: Float = 5.5f,
    val bike: Float = 0.41f,
)
