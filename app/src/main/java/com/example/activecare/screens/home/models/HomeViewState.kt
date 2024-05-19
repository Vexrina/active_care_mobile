package com.example.activecare.screens.home.models

import com.example.activecare.common.DateTimeParser
import com.example.activecare.dataclasses.FoodRecord
import com.example.activecare.dataclasses.HomeEventTuple
import com.example.activecare.dataclasses.Limitation
import com.example.activecare.dataclasses.Stat
import kotlinx.coroutines.channels.Channel
import java.time.LocalDateTime

data class HomeViewState(
    val homeSubState: HomeSubState = HomeSubState.Default,
    val stats: List<Stat> = emptyList(),
    val foodRecord: List<FoodRecord> = emptyList(),
    val isLoad: Boolean = true,
    val newWeight: String = "",
    val selectedDate: String ="",
    val limit: Limitation = Limitation(LocalDateTime.now().format(DateTimeParser)),

    val eventChannel: Channel<HomeEventTuple> = Channel(Channel.BUFFERED),
)
