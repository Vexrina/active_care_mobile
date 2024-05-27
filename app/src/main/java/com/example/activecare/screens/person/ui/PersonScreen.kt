package com.example.activecare.screens.person.ui

import android.util.Log
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
import com.example.activecare.common.simpleStringParser
import com.example.activecare.ui.components.BottomNavigationBar
import com.example.activecare.ui.components.Header
import com.example.activecare.navigation.NavigationTree
import com.example.activecare.screens.person.models.BluetoothViewState
import com.example.activecare.screens.person.models.PersonEvent
import com.example.activecare.screens.person.models.PersonSubState
import com.example.activecare.screens.person.models.PersonViewState
import com.example.activecare.screens.person.view.BluetoothDevicesView
import com.example.activecare.screens.person.view.DefaultView
import com.example.activecare.screens.person.view.NotificationView
import com.example.activecare.screens.person.view.ProfileSettings
import com.example.activecare.screens.person.view.SettingView
import com.example.activecare.screens.person.view.StatView
import com.example.activecare.screens.person.view.WorkoutView
import java.util.Calendar

@Composable
fun PersonScreen(
    personViewModel: PersonViewModel,
    navController: NavController,
) {
    val viewState: PersonViewState by personViewModel
        .viewState
        .collectAsState()
    val bluetoothViewState: BluetoothViewState by personViewModel
        .bluetoothState
        .collectAsState()
    with(viewState) {
        Scaffold(
            topBar = {
                Header(
                    text = when (personSubState) {
                        PersonSubState.Default -> stringResource(id = R.string.personHeaderDefault)
                        PersonSubState.Stat -> stringResource(id = R.string.personHeaderStat)
                        PersonSubState.Workouts -> stringResource(id = R.string.personHeaderWorkout)
                        PersonSubState.Recommendations -> stringResource(id = R.string.personHeaderRecommendations)
                        PersonSubState.Devices -> stringResource(id = R.string.personHeaderDevices)
                        PersonSubState.Settings -> stringResource(id = R.string.personHeaderSettings)
                        PersonSubState.ProfileSetting -> stringResource(id = R.string.SettingsProfile)
                        PersonSubState.NotificationSettings -> stringResource(id = R.string.SettingsNotifications)
                        PersonSubState.DeviceSettings -> stringResource(id = R.string.SettingsDevices)
                    },
                    backIcon = personSubState != PersonSubState.Default,
                    modifier = Modifier,
                    onClick = { personViewModel.obtainEvent(PersonEvent.BackClicked) },
                    textSize = 22.sp,
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    when (personSubState) {
                        PersonSubState.Default -> DefaultView(
                            viewState = this@with,
                            onStatViewClick = {
                                personViewModel.obtainEvent(PersonEvent.StatClicked)
                            },
                            onWorkoutViewClick = {
                                personViewModel.obtainEvent(PersonEvent.WorkoutClicked)
                            },
                            onSettingClick = {
                                personViewModel.obtainEvent(PersonEvent.SettingsClicked)
                            },
                            onDevicesClick = {
                                personViewModel.obtainEvent(PersonEvent.DevicesClicked)
                            },
                            loadData = {
                                personViewModel.obtainEvent(PersonEvent.LoadData(it))
                            }
                        )

                        PersonSubState.Stat -> StatView(
                            viewState = this@with,
                            onChangeDate = {
                                personViewModel.obtainEvent(
                                    PersonEvent.DateChanged(it)
                                )
                            },
                            onDataLoad ={
                                personViewModel.obtainEvent(
                                    PersonEvent.LoadData(it)
                                )
                            },
                            date = if (viewState.selectedDate == "") Calendar.getInstance()
                            else simpleStringParser(viewState.selectedDate)
                        )

                        PersonSubState.Workouts -> WorkoutView(
                            viewState = this@with,
                            onChangeDate = {
                                personViewModel.obtainEvent(
                                    PersonEvent.DateChanged(it)
                                )
                            },
                            onDataLoad ={
                                personViewModel.obtainEvent(
                                    PersonEvent.LoadData(it)
                                )
                            },
                            date = if (viewState.selectedDate == "") Calendar.getInstance()
                            else simpleStringParser(viewState.selectedDate)
                        )

                        PersonSubState.Recommendations -> TODO()
                        PersonSubState.Devices -> BluetoothDevicesView(
                            state = bluetoothViewState,
                            onStartScan = {
                                personViewModel.obtainEvent(PersonEvent.BluetoothStartScanClicked)
                            },
                            onDeviceClicked = {
                                personViewModel.obtainEvent(PersonEvent.BluetoothDeviceClicked(it))
                            },
                            onStopScan = {
                                personViewModel.obtainEvent(PersonEvent.BluetoothStopScanClicked)
                            },
                        )

                        PersonSubState.Settings -> SettingView(
                            onProfileClick = {
                                personViewModel.obtainEvent(PersonEvent.ProfileClicked)
                            },
                            onNotificationClick = {
                                personViewModel.obtainEvent(PersonEvent.NotificationsClicked)
                            },
                            onDevicesClick = {
                                personViewModel.obtainEvent(PersonEvent.DevicesSettingsClicked)
                            },
                            onLogoutClick = {
                                personViewModel.obtainEvent(PersonEvent.onLogOutClicked)
                                navController.navigate(NavigationTree.Onboard.name)
                            }
                        )

                        PersonSubState.ProfileSetting -> ProfileSettings(viewState = this@with)
                        PersonSubState.NotificationSettings -> NotificationView()
                        PersonSubState.DeviceSettings -> TODO()
                    }
                }
            },
            bottomBar = {
                BottomNavigationBar(
                    runClick = {
                        Log.d("BottomBar", "try to workout")
                        navController.navigate(NavigationTree.Workout.name)
                               },
                    homeClick = { navController.navigate(NavigationTree.Home.name) },
                    personClick = {},
                    activeIndex = 3
                )
            }
        )
    }
}