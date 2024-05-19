package com.example.activecare.common

import com.example.activecare.dataclasses.FoodRecord
import com.example.activecare.dataclasses.Stat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun filterByFoodTypeAndDate(
    foodType: Int,
    foodRecords: List<FoodRecord>,
    currentDate: Calendar
):List<FoodRecord>{
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(currentDate.time)
    return foodRecords.filter {
        it.foodtype == foodType
    }.filter { it.date_stamp.substring(0,10) == date }
}

fun filterFRByDate(
    foodRecords: List<FoodRecord>,
    currentDate: Calendar = Calendar.getInstance()
): List<FoodRecord>{
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(currentDate.time)
    return foodRecords.filter { it.date_stamp.substring(0,10) == date }
}

fun filterByDate(
    stats: List<Stat>,
    currentDate: Calendar = Calendar.getInstance()
): List<Stat>{
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(currentDate.time)
    return stats.filter { it.date_stamp.substring(0,10) == date }
}

fun earlyThanDate(
    stats: List<Stat>,
    currentDate: Calendar = Calendar.getInstance()
): List<Stat>{
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(currentDate.time)
    return stats.filter { it.date_stamp.substring(0,10) <= date }
}