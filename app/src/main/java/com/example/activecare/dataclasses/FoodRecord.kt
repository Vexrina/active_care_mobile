package com.example.activecare.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class FoodRecord(
    val foodname: String,
    val foodtype: Int,
    val date_stamp: String,
    val carbohydrates: Int,
    val fats: Int,
    val calories: Int,
    val proteins: Int,
    val user_id: String = "",
)
