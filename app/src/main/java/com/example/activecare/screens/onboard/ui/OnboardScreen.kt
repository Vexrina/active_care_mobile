package com.example.activecare.screens.onboard.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.activecare.R
import com.example.activecare.ui.components.CircleButton
import com.example.activecare.ui.components.Header
import com.example.activecare.ui.components.LinkComponent
import com.example.activecare.navigation.NavigationTree
import com.example.activecare.screens.onboard.models.OnboardEvent
import com.example.activecare.screens.onboard.models.OnboardSubState
import com.example.activecare.screens.onboard.models.OnboardViewState
import com.example.activecare.screens.onboard.view.ChooseView
import com.example.activecare.screens.onboard.view.DateView
import com.example.activecare.screens.onboard.view.GenderView
import com.example.activecare.screens.onboard.view.ValueView
import com.example.activecare.screens.signin.models.SignInEvent
import kotlinx.coroutines.channels.consumeEach

@Composable
fun OnboardScreen(
    onboardViewModel: OnboardViewModel,
    navController: NavController,
) {
    val viewState: OnboardViewState by onboardViewModel
        .viewState
        .collectAsState(initial = OnboardViewState())

    val context = LocalContext.current

    with(viewState) {
        Scaffold(
            topBar = {
                when (onboardSubState) {
                    OnboardSubState.Choose -> {}
                    else -> Header(
                        text = stringResource(id = R.string.onboardHeader),
                        modifier = Modifier,
                        backIcon = true,
                        onClick = { onboardViewModel.obtainEvent(OnboardEvent.ToChooseClicked) }
                    )
                }
            },
            content = { paddingValues ->
                LaunchedEffect(Unit) {
                    val eventChannel = viewState.eventChannel
                    eventChannel.consumeEach { event ->
                        when (event.Event) {
                            SignInEvent.ErrorShown -> Toast
                                .makeText(
                                    context, event.Message, Toast.LENGTH_SHORT
                                )
                                .show()

                            SignInEvent.ActionClicked -> navController.navigate(
                                "${NavigationTree.Login.name}/false"
                            )

                            else -> {}
                        }
                    }
                }
                Column(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    when (onboardSubState) {
                        OnboardSubState.Choose -> ChooseView(
                            viewState = this@with,
                            onSignUp = {
                                onboardViewModel.obtainEvent(OnboardEvent.NextClicked)
                            },
                            onLogIn = {
                                navController.navigate("${NavigationTree.Login.name}/true")
                            }
                        )

                        OnboardSubState.Gender -> GenderView(
                            viewState = this@with,
                            navController = navController,
                            onGenderClicked = {
                                onboardViewModel.obtainEvent(OnboardEvent.GenderClicked(it))
                            }
                        )

                        OnboardSubState.Weight -> {
                            ValueView(
                                viewState = this@with,
                                navController = navController,
                                value = this@with.weight,
                                onValueChange = {
                                    onboardViewModel.obtainEvent(OnboardEvent.WeightChanged(it))
                                }
                            )
                        }

                        OnboardSubState.Height -> ValueView(
                            viewState = this@with,
                            navController = navController,
                            value = this@with.height,
                            onValueChange = {
                                onboardViewModel.obtainEvent(OnboardEvent.HeightChanged(it))
                            }
                        )

                        OnboardSubState.Age -> DateView(
                            onDateChanged = {
                                onboardViewModel.obtainEvent(OnboardEvent.BirthDateChanged(it))
                            }
                        )
                    }
                }
            },
            bottomBar = {
                when (onboardSubState) {
                    OnboardSubState.Choose -> {}
                    OnboardSubState.Gender -> Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(bottom = 24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircleButton(
                            onClick = {
                                onboardViewModel.obtainEvent(OnboardEvent.NextClicked)
                            },
                            modifier = Modifier
                                .size(70.dp)
                        )
                        LinkComponent(
                            rowModifier = Modifier.padding(top = 12.dp),
                            commonText = "Есть учетная запись?",
                            linkText = "Войти",
                            linkAction = { /*TODO*/ },
                        )
                    }

                    else -> Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(start = 40.dp, end = 40.dp, bottom = 48.dp),
                    ) {
                        CircleButton(
                            onClick = {
                                onboardViewModel.obtainEvent(OnboardEvent.PrevClicked)
                            },
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .size(70.dp)
                                .rotate(180F)
                        )
                        CircleButton(
                            onClick = {
                                when (onboardSubState) {
                                    OnboardSubState.Age -> {
                                        onboardViewModel.obtainEvent(OnboardEvent.SaveToCache)
                                    }

                                    else -> {
                                        onboardViewModel.obtainEvent(OnboardEvent.NextClicked)
                                    }
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(70.dp)
                        )
                    }
                }
            }
        )
    }

}