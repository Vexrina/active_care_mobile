package com.example.activecare.network.data

object ApiRoutes {
    private const val BASE_URL = "http://10.0.2.2:8080"
    const val REGISTER = "$BASE_URL/user/register"
    const val LOGIN = "$BASE_URL/user/login"
    const val REFRESH_TOKEN = "$BASE_URL/user/update_token"
    const val APPEND_WATCH_STAT = "$BASE_URL/watch_stat/add"
    const val APPEND_FOOD_RECORD = "$BASE_URL/food_record/add"
    const val APPEND_WORKOUT = "$BASE_URL/workout/add"
    const val GET_WATCH_STAT = "$BASE_URL/watch_stat"
    const val GET_FOOD_RECORD = "$BASE_URL/food_record"
    const val GET_WORKOUT = "$BASE_URL/workout"
    const val GET_STATS = "$BASE_URL/stats"
}
