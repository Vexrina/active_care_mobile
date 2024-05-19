package com.example.activecare.screens.home.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.activecare.R
import com.example.activecare.common.simpleStringParser
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
import kotlinx.coroutines.channels.consumeEach
import java.util.Calendar

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

    LaunchedEffect(viewState.limit) {
        homeViewModel.obtainEvent(HomeEvent.LoadData(viewState.limit))
    }
    val context = LocalContext.current
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
                    if (viewState.isLoad){
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(top=100.dp)
                                .size(80.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                    else{
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
                                viewState = this@with,
                                limit = limit,
                                value = viewState.newWeight,
                                onValueChange = {
                                    homeViewModel.obtainEvent(HomeEvent.NewWeightChanged(it))
                                },
                                onChangeDate = {
                                    homeViewModel.obtainEvent(
                                        HomeEvent.DateChanged(it)
                                    )
                                },
                                onDataLoad ={
                                    homeViewModel.obtainEvent(
                                        HomeEvent.LoadData(it)
                                    )
                                },
                                date = if (viewState.selectedDate == "") Calendar.getInstance()
                                else simpleStringParser(viewState.selectedDate),
                                onSendData = {
                                    homeViewModel.obtainEvent(HomeEvent.AddWeight)
                                }
                            )

                            Sleep -> TODO()
                            Sp02 -> TODO()
                            Calories -> CaloriesView(
                                viewState = this@with,
                                limit = limit,
                                onAddCaloriesClicked = {
                                    homeViewModel.obtainEvent(
                                        HomeEvent.AddFoodRecordClicked(it)
                                    )
                                },
                                onChangeDate = {
                                    homeViewModel.obtainEvent(
                                        HomeEvent.DateChanged(it)
                                    )
                                },
                                onDataLoad ={
                                    homeViewModel.obtainEvent(
                                        HomeEvent.LoadData(it)
                                    )
                                },
                                date = if (viewState.selectedDate == "") Calendar.getInstance()
                                    else simpleStringParser(viewState.selectedDate)
                            )

                            Water -> WaterView(
                                viewState = this@with,
                                onChangeDate = {
                                    homeViewModel.obtainEvent(
                                        HomeEvent.DateChanged(it)
                                    )
                                },
                                onDataLoad ={
                                    homeViewModel.obtainEvent(
                                        HomeEvent.LoadData(it)
                                    )
                                },
                                date = if (viewState.selectedDate == "") Calendar.getInstance()
                                else simpleStringParser(viewState.selectedDate),
                                onChangeWater={
                                    homeViewModel.obtainEvent(
                                        HomeEvent.ChangeWater(it)
                                    )
                                },
                                limit = limit,
                            )
                            AddCalories -> {
                                LaunchedEffect(Unit) {
                                    val eventChannel = viewState.eventChannel
                                    eventChannel.consumeEach { event ->
                                        when (event.Event) {
                                            HomeEvent.ErrorShown -> Toast
                                                .makeText(
                                                    context, event.Message, Toast.LENGTH_SHORT
                                                )
                                                .show()

                                            HomeEvent.BackClicked -> {
                                            }
                                            else -> {}
                                        }
                                    }
                                }
                                AddFoodRecordView(
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
                                    onAddFoodRecordClicked = {
                                        homeViewModel.obtainEvent(HomeEvent.AddFoodRecordClicked(it))
                                    }
                                )
                            }
                        }
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