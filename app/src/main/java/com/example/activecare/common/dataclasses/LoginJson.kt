package com.example.activecare.common.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class LoginJson(
    val email: String,
    val password: String,
)
