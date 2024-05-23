package com.example.activecare.common.dataclasses

data class Limitation(
    val date: String,
    val date_offset: Int = 7,
    val deltatype: String = "day"
)
