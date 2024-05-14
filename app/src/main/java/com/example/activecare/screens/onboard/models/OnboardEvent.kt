package com.example.activecare.screens.onboard.models


sealed class OnboardEvent {
    data class GenderClicked(val value: Boolean) : OnboardEvent()
    data class WeightChanged(val value: String) : OnboardEvent()
    data class HeightChanged(val value: String) : OnboardEvent()
    data class BirthDateChanged(val value: String) : OnboardEvent()
    data object NextClicked : OnboardEvent()
    data object PrevClicked : OnboardEvent()
    data object ToChooseClicked : OnboardEvent()
    data object SaveToCache : OnboardEvent()
}
