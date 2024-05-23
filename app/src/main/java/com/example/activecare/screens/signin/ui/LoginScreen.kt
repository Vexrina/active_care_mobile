package com.example.activecare.screens.signin.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.activecare.ui.components.Header
import com.example.activecare.ui.components.LinkComponent
import com.example.activecare.navigation.NavigationTree
import com.example.activecare.screens.signin.models.SignInEvent
import com.example.activecare.screens.signin.models.SignInSubState
import com.example.activecare.screens.signin.models.SignInViewState
import com.example.activecare.screens.signin.view.ForgotEndView
import com.example.activecare.screens.signin.view.ForgotStartView
import com.example.activecare.screens.signin.view.SignInView
import com.example.activecare.screens.signin.view.SignUpEndView
import com.example.activecare.screens.signin.view.SignUpStartView
import kotlinx.coroutines.channels.consumeEach

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navController: NavController,
) {
    val viewState: SignInViewState by loginViewModel
        .viewState
        .collectAsState(
            initial = SignInViewState()
        )

    val context = LocalContext.current
    with(viewState) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            topBar = {
                Header(
                    text = when (signInSubState) {
                        SignInSubState.SignIn -> stringResource(id = R.string.sign_in_header)
                        SignInSubState.SignUpStart -> stringResource(id = R.string.sign_up_header)
                        SignInSubState.SignUpEnd -> stringResource(id = R.string.sign_up_header)
                        SignInSubState.ForgotStart -> stringResource(id = R.string.forgot_header)
                        SignInSubState.ForgotEnd -> stringResource(id = R.string.forgot_header)
                    },
                    backIcon = true,
                    onClick = {
                        when (signInSubState) {
                            SignInSubState.SignIn -> {
                                navController.navigate("onboard")
                            }

                            SignInSubState.SignUpStart -> {
                                loginViewModel.obtainEvent(SignInEvent.ActionClicked)
                            }

                            SignInSubState.SignUpEnd -> {
                                loginViewModel.obtainEvent(SignInEvent.SignUpBack)
                            }

                            SignInSubState.ForgotStart -> {
                                loginViewModel.obtainEvent(SignInEvent.ForgotBack)
                            }

                            SignInSubState.ForgotEnd -> {
                                loginViewModel.obtainEvent(SignInEvent.ForgotBack)
                            }
                        }
                    },
                    modifier = Modifier
                        .width(327.dp),
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(Color.White)
                ) {
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
                                    NavigationTree.Home.name
                                )

                                else -> {}
                            }
                        }
                    }

                    when (signInSubState) {
                        SignInSubState.SignIn -> {
                            SignInView(
                                viewState = this@with,
                                onEmailFieldChange = {
                                    loginViewModel.obtainEvent(SignInEvent.EmailChanged(it))
                                },
                                onPasswordFieldChange = {
                                    loginViewModel.obtainEvent(SignInEvent.PasswordChanged(it))
                                },
                                onForgetClick = {
                                    loginViewModel.obtainEvent(SignInEvent.ForgetClicked)
                                },
                                onLoginClick = {
                                    loginViewModel.obtainEvent(SignInEvent.LoginClicked)
                                },
                            )
                        }

                        SignInSubState.SignUpStart -> {
                            SignUpStartView(
                                viewState = this@with,
                                onEmailFieldChange = {
                                    loginViewModel.obtainEvent(SignInEvent.EmailChanged(it))
                                },
                                onUsernameFieldChange = {
                                    loginViewModel.obtainEvent(SignInEvent.UsernameChanged(it))
                                },
                                onRegisterClick = {
                                    loginViewModel.obtainEvent(SignInEvent.RegisterClicked)
                                },
                            )
                        }

                        SignInSubState.SignUpEnd -> {
                            SignUpEndView(
                                viewState = this@with,
                                onPasswordFieldChange = {
                                    loginViewModel.obtainEvent(SignInEvent.PasswordChanged(it))
                                },
                                onPasswordConfirmFieldChange = {
                                    loginViewModel.obtainEvent(SignInEvent.ConfirmPasswordChanged(it))
                                },
                                onRegisterClick = {
                                    loginViewModel.obtainEvent(SignInEvent.RegisterClicked)
                                }
                            )
                        }

                        SignInSubState.ForgotStart -> {
                            ForgotStartView(
                                viewState = viewState,
                                onEmailFieldChange = {
                                    loginViewModel.obtainEvent(SignInEvent.EmailChanged(it))
                                },
                                onButtonClick = {
                                    loginViewModel.obtainEvent(SignInEvent.ForgetContinueButtonClicked)
                                }
                            )
                        }

                        SignInSubState.ForgotEnd -> {
                            ForgotEndView(
                                viewState = viewState,
                            )
                        }
                    }
                }

            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    LinkComponent(
                        commonText = when (signInSubState) {
                            SignInSubState.SignUpStart -> stringResource(id = R.string.sign_up_change)
                            SignInSubState.SignUpEnd -> stringResource(id = R.string.sign_up_change)
                            SignInSubState.SignIn -> stringResource(id = R.string.sign_in_change)
                            SignInSubState.ForgotStart -> stringResource(id = R.string.sign_in_change)
                            SignInSubState.ForgotEnd -> ""
                        },
                        linkText = when (signInSubState) {
                            SignInSubState.SignUpStart -> stringResource(id = R.string.sign_up_change_link)
                            SignInSubState.SignUpEnd -> stringResource(id = R.string.sign_up_change_link)
                            SignInSubState.SignIn -> stringResource(id = R.string.sign_in_change_link)
                            SignInSubState.ForgotStart -> stringResource(id = R.string.sign_in_change_link)
                            SignInSubState.ForgotEnd -> ""
                        },
                        rowModifier = Modifier
                            .padding(top = 24.dp)
                    ) {
                        loginViewModel.obtainEvent(SignInEvent.ActionClicked)
                    }
                }
            }
        )
    }
}