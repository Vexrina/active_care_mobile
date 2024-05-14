package com.example.activecare.dataclasses

data class ActivityStat(
    val dateStamp: String = "7 may",
    val steps: Int = 500,
    val distance: Float = 0.44f,
    val calories: Int = 2500,
)
