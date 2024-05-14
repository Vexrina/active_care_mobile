package com.example.activecare.screens.signin.models

sealed class SignInEvent {
    data object None : SignInEvent()
    data object LoginActionInvoked : SignInEvent()
    data object ActionClicked : SignInEvent()
    data object SignUpBack : SignInEvent()
    data object ErrorShown : SignInEvent()
    data object LoginClicked : SignInEvent()
    data object RegisterClicked : SignInEvent()
    data object ForgetClicked : SignInEvent()
    data object ForgetContinueButtonClicked : SignInEvent()
    data object ForgotBack : SignInEvent()
    data class EmailChanged(val value: String) : SignInEvent()
    data class PasswordChanged(val value: String) : SignInEvent()
    data class ConfirmPasswordChanged(val value: String) : SignInEvent()
    data class UsernameChanged(val value: String) : SignInEvent()
}