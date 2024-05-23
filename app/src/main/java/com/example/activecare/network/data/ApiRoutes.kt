package com.example.activecare.network.data

object ApiRoutes {
    private const val BASE_URL = "http://10.0.2.2:8080"
    const val USER = "$BASE_URL/user"
    const val REGISTER = "$USER/register"
    const val LOGIN = "$USER/login"
    const val REFRESH_TOKEN = "$USER/update_token"
    const val WATCH_STAT = "$BASE_URL/watch_stat"
    const val FOOD_RECORD = "$BASE_URL/food_record"
    const val WORKOUT = "$BASE_URL/workout"
    const val STATS = "$BASE_URL/stat"
}
