package com.example.activecare.common.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val access_token: String,
    val token_type: String,
)
