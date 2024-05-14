package com.example.activecare.screens.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.activecare.R
import com.example.activecare.components.BottomNavigationBar
import com.example.activecare.components.Header
import com.example.activecare.navigation.NavigationTree
import com.example.activecare.screens.home.models.AddCaloriesState
import com.example.activecare.screens.home.models.HomeEvent
import com.example.activecare.screens.home.models.HomeSubState.AddCalories
import com.example.activecare.screens.home.models.HomeSubState.Calories
import com.example.activecare.screens.home.models.HomeSubState.Default
import com.example.activecare.screens.home.models.HomeSubState.Pulse
import com.example.activecare.screens.home.models.HomeSubState.Sleep
import com.example.activecare.screens.home.models.HomeSubState.Sp02
import com.example.activecare.screens.home.models.HomeSubState.Water
import com.example.activecare.screens.home.models.HomeSubState.Weight
import com.example.activecare.screens.home.models.HomeViewState
import com.example.activecare.screens.home.view.AddFoodRecordView
import com.example.activecare.screens.home.view.CaloriesView
import com.example.activecare.screens.home.view.DefaultView
import com.example.activecare.screens.home.view.PlotView
import com.example.activecare.screens.home.view.WaterView
import com.example.activecare.screens.home.view.WeightView

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    val viewState: HomeViewState by homeViewModel
        .viewState
        .collectAsState()

    val caloriesState: AddCaloriesState by homeViewModel
        .addCaloriesState
        .collectAsState()

    with(viewState) {
        Scaffold(
            topBar = {
                Header(
                    text = when (homeSubState) {
                        Default -> stringResource(id = R.string.homeHeaderDefault)
                        Pulse -> stringResource(id = R.string.homeHeaderPulse)
                        Weight -> stringResource(id = R.string.homeHeaderWeight)
                        Sleep -> stringResource(id = R.string.homeHeaderSleep)
                        Sp02 -> stringResource(id = R.string.homeHeaderSpO2)
                        Calories -> stringResource(id = R.string.homeHeaderCalories)
                        Water -> stringResource(id = R.string.homeHeaderWater)
                        AddCalories -> caloriesState.foodType
                    },
                    modifier = Modifier,
                    backIcon = homeSubState != Default,
                    onClick = { homeViewModel.obtainEvent(HomeEvent.BackClicked) }
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(Color.White),
                ) {
                    when (homeSubState) {
                        Default -> DefaultView(
                            viewState = this@with,
                            onClickEvents = listOf(
                                { homeViewModel.obtainEvent(HomeEvent.PulseClicked) },
                                { homeViewModel.obtainEvent(HomeEvent.WeightClicked) },
                                { homeViewModel.obtainEvent(HomeEvent.SleepClicked) },
                                { homeViewModel.obtainEvent(HomeEvent.SpO2Clicked) },
                                { homeViewModel.obtainEvent(HomeEvent.CaloriesClicked) },
                                { homeViewModel.obtainEvent(HomeEvent.WaterClicked) },
                            )
                        )

                        Pulse -> PlotView()
                        Weight -> WeightView(
                            value = viewState.newWeight,
                            onValueChange = {
                                homeViewModel.obtainEvent(HomeEvent.NewWeightChanged(it))
                            }
                        )
                        Sleep -> TODO()
                        Sp02 -> TODO()
                        Calories -> CaloriesView(
                            viewState = this@with,
                            onAddCaloriesClicked = {
                                homeViewModel.obtainEvent(
                                    HomeEvent.AddFoodRecordClicked(it)
                                )
                            }
                        )
                        Water -> WaterView(viewState=this@with)
                        AddCalories -> AddFoodRecordView(
                            caloriesState = caloriesState,
                            onFoodNameChanged = {
                                homeViewModel.obtainEvent(
                                    HomeEvent.FoodNameChanged(it)
                                )
                            },
                            onCaloriesChanged = {
                                homeViewModel.obtainEvent(
                                    HomeEvent.CaloriesChanged(it)
                                )
                            },
                            onProteinsChanged = {
                                homeViewModel.obtainEvent(
                                    HomeEvent.ProteinsChanged(it)
                                )
                            },
                            onFatsChanged = {
                                homeViewModel.obtainEvent(
                                    HomeEvent.FatsChanged(it)
                                )
                            },
                            onCarbohydratesChanged = {
                                homeViewModel.obtainEvent(
                                    HomeEvent.CarbohydratesChanged(it)
                                )
                            },
                            onAddFoodRecordClicked = {}
                        )
                    }
                }
            },
            bottomBar = {
                BottomNavigationBar(
                    runClick = { navController.navigate(NavigationTree.Workout.name) },
                    homeClick = {},
                    personClick = { navController.navigate(NavigationTree.Me.name) },
                    activeIndex = 2
                )
            }
        )
    }
}