package com.example.activecare.common.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String = "Pavlova Lada",
    val email: String = "",
    val password: String = "",
    val gender: Boolean,
    val weight: Float = 0.0f,
    val height: Float = 0.0f,
    val birthdate: String = "03/05/2002",
)
