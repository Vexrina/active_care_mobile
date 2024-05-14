package com.example.activecare.dataclasses

import com.example.activecare.screens.signin.models.SignInEvent

data class SignInEventTuple(
    val Event: SignInEvent,
    val Message: String,
)
