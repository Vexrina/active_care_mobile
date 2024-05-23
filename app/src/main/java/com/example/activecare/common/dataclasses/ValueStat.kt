package com.example.activecare.common.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class ValueStat(
    val date: String,
    val value: Float,
)
