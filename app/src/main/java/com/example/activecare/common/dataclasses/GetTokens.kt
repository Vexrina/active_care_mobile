package com.example.activecare.common.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class GetTokens(
    val access_token: Token,
    val refresh_token: Token
)
