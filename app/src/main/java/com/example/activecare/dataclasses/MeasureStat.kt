package com.example.activecare.dataclasses

data class MeasureStat(
    val dateStamp: String = "7 may",
    val weight: Float = 65.5f,
    val sleep: Int = 8,
    val pulse: Int = 87,
    val spO2: Int = 96,
    val calories: Int = 2500,
)
