package com.example.activecare.network.domain

import com.example.activecare.cache.domain.Cache
import com.example.activecare.dataclasses.FoodRecord
import com.example.activecare.dataclasses.GetTokens
import com.example.activecare.dataclasses.LoginJson
import com.example.activecare.dataclasses.Token
import com.example.activecare.dataclasses.User
import com.example.activecare.dataclasses.WatchStat
import com.example.activecare.dataclasses.Workout
import com.example.activecare.network.data.ApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json

interface ApiService {
    suspend fun login(loginData: LoginJson): Pair<List<Token>?, Error?>
    suspend fun updateToken(): Error?
    suspend fun createUser(user: User): Pair<GetTokens?, Error?>

    suspend fun appendUserData(data: WatchStat): Pair<WatchStat?, Error?>
    suspend fun appendUserData(data: FoodRecord): Pair<FoodRecord?, Error?>
    suspend fun appendUserData(data: Workout): Pair<Workout?, Error?>

    suspend fun getUserData(userId: String, dataType: String): Pair<Any?, Error?>

    suspend fun getData(url: String): Pair<Any?, Error?>

    companion object {
        fun create(cache: Cache): ApiService {
            return ApiServiceImpl(
                client = HttpClient(Android) {
                    // JSON
                    install(ContentNegotiation) {
                        json()
                    }
                    // ready to take errors
                    expectSuccess = true
                    // Timeout
                    install(HttpTimeout) {
                        requestTimeoutMillis = 15000L
                        connectTimeoutMillis = 15000L
                        socketTimeoutMillis = 15000L
                    }
                    // Apply to all requests
                    defaultRequest {
                        // Content Type
                        accept(ContentType.Application.Json)
                    }
                },
                cache = cache
            )
        }

        private val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
    }

}