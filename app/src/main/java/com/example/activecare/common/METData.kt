package com.example.activecare.common

private val WalkingMap = hashMapOf(
    3.2f to 2f,
    4f   to 2.8f,
    4.8f to 3.3f,
    5.6f to 4.3f,
    6.4f to 5f,
)

private val RunningMap = hashMapOf(
    6.4f to 6f,
    8f   to 8.3f,
    9.7f to 9.8f,
    11.3f to 11f,
    12.9f to 11.8f,
)

private val BikeMap = hashMapOf(
    16f to 3.5f,
    19f   to 6.8f,
    22.5f to 8f,
    26f to 10f,
    30f to 12f,
)

private fun findClosest(value: Float, map: HashMap<Float, Float>): Float{
    val keysList: List<Float> = map.keys.toList()
    val closest = keysList.minBy {
        kotlin.math.abs(it - value)
    }
    return map[closest] ?: 0f
}

fun calculateBurnedCalories(seconds: Int, distance: Float, workoutType: Int, weight: Float) : Float{
    val speed: Float = distance/seconds
    val met = when(workoutType){
        0 -> findClosest(speed, RunningMap)
        1-> findClosest(speed, RunningMap)
        2-> findClosest(speed, WalkingMap)
        3 -> findClosest(speed, BikeMap)
        else -> 0f
    }
    return met*weight*seconds/3600f
}