package com.example.activecare.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    val date_stamp: String,
    val pulse: Float,
    val steps: Int,
    val oxygen_blood: Float,
    val sleep: Int,
    val weight: Float,
    val water: Int,
)
