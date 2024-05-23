package com.example.activecare.screens.onboard.models

import com.example.activecare.common.dataclasses.EventTuple
import kotlinx.coroutines.channels.Channel

data class OnboardViewState(
    val onboardSubState: OnboardSubState = OnboardSubState.Choose,
    val female: Boolean? = null,
    val weight: String? = null,
    val height: String? = null,
    val birthdate: String? = null,

    val eventChannel: Channel<EventTuple> = Channel(Channel.BUFFERED),
)
