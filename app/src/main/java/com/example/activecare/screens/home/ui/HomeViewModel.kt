package com.example.activecare.screens.home.ui

import androidx.lifecycle.ViewModel
import com.example.activecare.common.EventHandler
import com.example.activecare.network.domain.ApiService
import com.example.activecare.screens.home.models.AddCaloriesState
import com.example.activecare.screens.home.models.HomeEvent
import com.example.activecare.screens.home.models.HomeSubState
import com.example.activecare.screens.home.models.HomeViewState
import com.example.activecare.screens.person.models.PersonSubState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    apiService: ApiService,
) : ViewModel(), EventHandler<HomeEvent> {

    private val _viewState: MutableStateFlow<HomeViewState> = MutableStateFlow(HomeViewState())
    val viewState: StateFlow<HomeViewState> = _viewState

    private val _addCaloriesState: MutableStateFlow<AddCaloriesState> = MutableStateFlow(
        AddCaloriesState()
    )
    val addCaloriesState: StateFlow<AddCaloriesState> = _addCaloriesState

    override fun obtainEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.CaloriesClicked -> changeSubState(HomeSubState.Calories)
            HomeEvent.PulseClicked -> changeSubState(HomeSubState.Pulse)
            HomeEvent.SleepClicked -> changeSubState(HomeSubState.Sleep)
            HomeEvent.SpO2Clicked -> changeSubState(HomeSubState.Sp02)
            HomeEvent.WaterClicked -> changeSubState(HomeSubState.Water)
            HomeEvent.WeightClicked -> changeSubState(HomeSubState.Weight)
            HomeEvent.BackClicked -> backClicked()
            is HomeEvent.AddFoodRecordClicked -> addFoodRecord(event.value)
            is HomeEvent.CaloriesChanged -> caloriesChanged(event.value)
            is HomeEvent.CarbohydratesChanged -> carbohydratesChanged(event.value)
            is HomeEvent.FatsChanged -> fatsChanged(event.value)
            is HomeEvent.FoodNameChanged -> foodNameChanged(event.value)
            is HomeEvent.ProteinsChanged -> proteinsChanged(event.value)
            is HomeEvent.NewWeightChanged -> weightChanged(event.value)
        }
    }

    private fun backClicked(){
        when (_viewState.value.homeSubState){
            HomeSubState.AddCalories -> changeSubState(HomeSubState.Calories)
            else -> changeSubState(HomeSubState.Default)
        }
    }

    private fun changeSubState(newSubState: HomeSubState) {
        _viewState.update {
            it.copy(
                homeSubState = newSubState
            )
        }
    }

    private fun addFoodRecord(value: String){
        if (_viewState.value.homeSubState != HomeSubState.AddCalories) {
            _viewState.update {
                it.copy(
                    homeSubState = HomeSubState.AddCalories,
                )
            }
            _addCaloriesState.update {
                it.copy(
                    foodType = value
                )
            }
        } else {
            TODO()
        }
    }

    private fun caloriesChanged(value: String){
        _addCaloriesState.update {
            it.copy(
                calories = value
            )
        }
    }
    private fun carbohydratesChanged(value: String){
        _addCaloriesState.update {
            it.copy(
                carbohydrates = value
            )
        }
    }
    private fun fatsChanged(value: String){
        _addCaloriesState.update {
            it.copy(
                fats = value
            )
        }
    }
    private fun foodNameChanged(value: String){
        _addCaloriesState.update {
            it.copy(
                foodName = value
            )
        }
    }
    private fun proteinsChanged(value: String){
        _addCaloriesState.update {
            it.copy(
                proteins = value
            )
        }
    }
    private fun weightChanged(value: String) {
        _viewState.update {
            it.copy(
                newWeight = value
            )
        }
    }

    private fun showCalories() {
        TODO()
    }

    private fun showPulse() {
        TODO()
    }

    private fun showSleep() {
        TODO()
    }

    private fun showSpO2() {
        TODO()
    }

    private fun showWater() {
        TODO()
    }

    private fun showWeight() {
        TODO()
    }
}