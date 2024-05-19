package com.example.activecare.dataclasses

data class Limitation(
    val date: String,
    val date_offset: Int = 7,
    val deltatype: String = "day"
)
