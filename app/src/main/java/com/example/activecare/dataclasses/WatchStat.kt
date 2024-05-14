package com.example.activecare.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class WatchStat(
    val id: String? = null,
    val userid: String,
    val sleep: Int,
    val oxygen_blood: Int,
    val steps: Int,
    val pulse: Int,
    val date_stamp: String,
)
