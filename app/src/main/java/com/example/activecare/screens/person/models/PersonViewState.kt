package com.example.activecare.screens.person.models

import com.example.activecare.dataclasses.ActivityStat
import com.example.activecare.dataclasses.ActivityWorkout
import com.example.activecare.dataclasses.MeasureStat
import com.example.activecare.dataclasses.MeasureWorkout
import com.example.activecare.dataclasses.User

data class PersonViewState(
    val personSubState: PersonSubState = PersonSubState.Default,
    val stats: Pair<ActivityStat, MeasureStat>? = Pair(ActivityStat(), MeasureStat()),
    val workout: Pair<ActivityWorkout, MeasureWorkout>? = Pair(ActivityWorkout(), MeasureWorkout()),
    val user: User? = User(),
)