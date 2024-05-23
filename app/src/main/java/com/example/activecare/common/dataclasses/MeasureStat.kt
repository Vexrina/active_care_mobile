package com.example.activecare.common.dataclasses

data class MeasureStat(
    val date_stamp: String = "7 may",
    val weight: Float = 65.5f,
    val sleep: Int = 8,
    val pulse: Int = 87,
    val spO2: Int = 96,
    val calories: Int = 2500,
)
