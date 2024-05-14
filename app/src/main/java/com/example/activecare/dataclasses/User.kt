package com.example.activecare.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val username: String = "Pavlova Lada",
    val email: String = "",
    val password: String = "",
    val gender: Boolean = false,
    val weight: Float = 0.0f,
    val height: Float = 0.0f,
    val birthdate: String = "03/05/2002",
)
