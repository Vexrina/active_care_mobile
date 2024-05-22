package com.example.activecare.common

import com.example.activecare.dataclasses.FoodRecord
import com.example.activecare.dataclasses.Stat
import com.example.activecare.dataclasses.Workout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun filterByFoodTypeAndDate(
    foodType: Int,
    foodRecords: List<FoodRecord>,
    currentDate: Calendar,
): List<FoodRecord> {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(currentDate.time)
    return foodRecords.filter {
        it.foodtype == foodType
    }.filter { it.date_stamp.substring(0, 10) == date }
}

fun filterFRByDate(
    foodRecords: List<FoodRecord>,
    currentDate: Calendar = Calendar.getInstance(),
): List<FoodRecord> {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(currentDate.time)
    return foodRecords.filter { it.date_stamp.substring(0, 10) == date }
}

fun filterByDate(
    stats: List<Stat>,
    currentDate: Calendar = Calendar.getInstance(),
): List<Stat> {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(currentDate.time)
    return stats.filter { it.date_stamp.substring(0, 10) == date }
}

fun earlyThanDate(
    stats: List<Stat>,
    currentDate: Calendar = Calendar.getInstance(),
): List<Stat> {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(currentDate.time)
    return stats.filter { it.date_stamp.substring(0, 10) <= date }
}

fun averageInStats(
    stats: List<Stat>,
): Pair<Float, Float> {
    var sumPulse = 0f
    var sizePulse = 0
    var sumSpO2 = 0f
    var sizeSpO2 = 0
    stats.forEach {
        if(it.pulse!=0f){
            sumPulse += it.pulse
            sizePulse++
        }
        if(it.oxygen_blood!=0f){
            sumSpO2 += it.oxygen_blood
            sizeSpO2++
        }
    }
    return Pair(sumPulse / sizePulse, sumSpO2 / sizeSpO2)
}

fun filterWorkoutsByWorkoutTypeAndCalculateSum(
    workouts: List<Workout>,
    workoutType: Int,
): Float {
    return workouts
        .filter {
            it.workout_type == workoutType
        }
        .sumOf {
            it.distance.toDouble()
        }
        .toFloat()
}