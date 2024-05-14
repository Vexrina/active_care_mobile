package com.example.activecare.screens.signin.models

import com.example.activecare.dataclasses.SignInEventTuple
import kotlinx.coroutines.channels.Channel

data class SignInViewState(
    val signInSubState: SignInSubState = SignInSubState.SignIn,
    val emailValue: String = "",
    val passwordValue: String = "",
    val confirmPasswordValue: String = "",
    val usernameValue: String = "",
    val rememberMeChecked: Boolean = false,
    val isProgress: Boolean = false,
    val loginAction: SignInAction = SignInAction.None,
    val phoneNumberValue: String = "",
    val errorMessage: String? = null,
    val eventChannel: Channel<SignInEventTuple> = Channel(Channel.BUFFERED),
)
