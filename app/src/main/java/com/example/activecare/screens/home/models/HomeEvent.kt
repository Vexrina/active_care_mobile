package com.example.activecare.screens.home.models

import com.example.activecare.common.dataclasses.Limitation

sealed class HomeEvent {
    data class LoadData(val value: Limitation) : HomeEvent()
    data object PulseClicked : HomeEvent()
    data object WeightClicked : HomeEvent()
    data object SleepClicked : HomeEvent()
    data object SpO2Clicked : HomeEvent()
    data object CaloriesClicked : HomeEvent()
    data object WaterClicked : HomeEvent()
    data object BackClicked : HomeEvent()
    data class AddFoodRecordClicked(val value: String) : HomeEvent()
    data class FoodNameChanged(val value: String) : HomeEvent()
    data class CaloriesChanged(val value: String) : HomeEvent()
    data class ProteinsChanged(val value: String) : HomeEvent()
    data class CarbohydratesChanged(val value: String) : HomeEvent()
    data class FatsChanged(val value: String) : HomeEvent()
    data class NewWeightChanged(val value: String) : HomeEvent()
    data class DateChanged(val value: String) : HomeEvent()
    data object AddWeight : HomeEvent()
    data class ChangeWater(val value: Int) : HomeEvent()
    data object ErrorShown : HomeEvent()
    data class SleepChanged(val value: String) : HomeEvent()
    data object SleepSend : HomeEvent()
}
