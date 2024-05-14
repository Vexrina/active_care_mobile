package com.example.activecare.screens.home.models

sealed class HomeEvent {
    data object PulseClicked : HomeEvent()
    data object WeightClicked : HomeEvent()
    data object SleepClicked : HomeEvent()
    data object SpO2Clicked : HomeEvent()
    data object CaloriesClicked : HomeEvent()
    data object WaterClicked : HomeEvent()
    data object BackClicked : HomeEvent()
    data class AddFoodRecordClicked(val value: String) : HomeEvent()
    data class FoodNameChanged(val value: String): HomeEvent()
    data class CaloriesChanged(val value: String): HomeEvent()
    data class ProteinsChanged(val value: String): HomeEvent()
    data class CarbohydratesChanged(val value: String): HomeEvent()
    data class FatsChanged(val value: String): HomeEvent()
    data class NewWeightChanged(val value: String): HomeEvent()
}
