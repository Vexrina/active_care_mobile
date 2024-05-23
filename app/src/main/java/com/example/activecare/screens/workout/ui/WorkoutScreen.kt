package com.example.activecare.screens.workout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.activecare.R
import com.example.activecare.ui.components.BottomNavigationBar
import com.example.activecare.ui.components.Header
import com.example.activecare.navigation.NavigationTree
import com.example.activecare.screens.workout.models.WorkoutEvent
import com.example.activecare.screens.workout.models.WorkoutSubState
import com.example.activecare.screens.workout.models.WorkoutViewState
import com.example.activecare.screens.workout.views.DefaultView
import com.example.activecare.screens.workout.views.OsmdroidMap
import com.example.activecare.screens.workout.views.TrackView

@Composable
fun WorkoutScreen(
    workoutViewModel: WorkoutViewModel,
    navController: NavController,
){
    val viewState: WorkoutViewState by workoutViewModel
        .viewState
        .collectAsState()

    with(viewState){
        Scaffold(
            topBar = {
                Header(
                    text = when(workoutSubState) {
                        WorkoutSubState.Default -> stringResource(id = R.string.WorkoutTitle)
                        WorkoutSubState.StreetRun -> stringResource(id = R.string.StreetRun)
                        WorkoutSubState.TrackRun -> stringResource(id = R.string.TrackRun)
                        WorkoutSubState.Walking -> stringResource(id = R.string.Walking)
                        WorkoutSubState.Bike -> stringResource(id = R.string.Bike)
                        WorkoutSubState.WorkoutProccesing -> TODO()
                    },
                    backIcon = workoutSubState != WorkoutSubState.Default,
                    modifier = Modifier,
                    onClick = { workoutViewModel.obtainEvent(WorkoutEvent.BackClicked)},
                    textSize = 22.sp,
                )
            },
            content = {paddingValues->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    when (workoutSubState) {
                        WorkoutSubState.Default -> DefaultView(
                            onStreetRunClick = { workoutViewModel.obtainEvent(WorkoutEvent.StreetRun)},
                            onTrackRunClick = { workoutViewModel.obtainEvent(WorkoutEvent.TrackRun)},
                            onWalkingClick = { workoutViewModel.obtainEvent(WorkoutEvent.Walking)},
                            onBikeClick = { workoutViewModel.obtainEvent(WorkoutEvent.Bike)},
                        )
                        WorkoutSubState.TrackRun -> TrackView(
                            viewState = this@with,
                            onTimeStartChanged = {
                                workoutViewModel.obtainEvent(WorkoutEvent.TimeStartChanged(it))
                            },
                            onTimeEndChanged = {
                                workoutViewModel.obtainEvent(WorkoutEvent.TimeEndChanged(it))
                            },
                            onDistanceChanged = {
                                workoutViewModel.obtainEvent(WorkoutEvent.DistanceChanged(it))
                            },
                            onCaloriesChanged = {
                                workoutViewModel.obtainEvent(WorkoutEvent.CaloriesChanged(it))
                            },
                        )
                        else -> {
                            OsmdroidMap(
                                viewState=this@with,
                                drawLineEvent = {
                                    workoutViewModel.obtainEvent(WorkoutEvent.StartWorkout(it))
                                },
                                onPauseClicked = {
                                    workoutViewModel.obtainEvent(WorkoutEvent.PauseWorkout)
                                },
                                onContinueClicked = {
                                    workoutViewModel.obtainEvent(WorkoutEvent.ContinueWorkout(it))
                                },
                                workoutStarted = {
                                    workoutViewModel.obtainEvent(WorkoutEvent.ChangeViewOnWorkoutStarted)
                                },
                                onSendDataClicked = {
                                    workoutViewModel.obtainEvent(WorkoutEvent.SendData)
                                }
                            )
                        }
                    }
                }
            },
            bottomBar = {
                BottomNavigationBar(
                    runClick = {},
                    homeClick = { navController.navigate(NavigationTree.Home.name) },
                    personClick = { navController.navigate(NavigationTree.Me.name) },
                    activeIndex = 1,
                )
            }
        )
    }
}