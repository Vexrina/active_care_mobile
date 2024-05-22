package com.example.activecare.screens.person.models

import com.example.activecare.common.EndDateParser
import com.example.activecare.dataclasses.ActivityStat
import com.example.activecare.dataclasses.ActivityWorkout
import com.example.activecare.dataclasses.Limitation
import com.example.activecare.dataclasses.MeasureStat
import com.example.activecare.dataclasses.MeasureWorkout
import com.example.activecare.dataclasses.User
import java.time.LocalDateTime

data class PersonViewState(
    val personSubState: PersonSubState = PersonSubState.Default,
    val stats: Pair<ActivityStat, MeasureStat> = Pair(ActivityStat(), MeasureStat()),
    val workout: Pair<ActivityWorkout, MeasureWorkout> = Pair(ActivityWorkout(), MeasureWorkout()),
    val user: User? = User(),
    val isLoad: Boolean = false,
    val selectedDate: String = "",
    val limit: Limitation = Limitation(LocalDateTime.now().format(EndDateParser), 0)
)