package com.example.activecare.screens.home.models

import com.example.activecare.dataclasses.ValueStat

data class HomeViewState(
    val homeSubState: HomeSubState = HomeSubState.Default,
    val pulses: List<ValueStat> = emptyList(),
    val weights: List<ValueStat> = emptyList(),
    val sleeps: List<ValueStat> = emptyList(),
    val spo2: List<ValueStat> = emptyList(),
    val calories: List<ValueStat> = emptyList(),
    val water: List<ValueStat> = emptyList(),
    val isLoad: Boolean = false,
    val newWeight: String = ""
)
