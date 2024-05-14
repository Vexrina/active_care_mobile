package com.example.activecare.dataclasses

data class FoodRecord(
    val id: String? = null,
    val userid: String,
    val foodName: String,
    val foodType: String,
    val carbohydrates: Int,
    val fats: Int,
    val calories: Int,
    val proteins: Int,
    val date_stamp: String,
)
