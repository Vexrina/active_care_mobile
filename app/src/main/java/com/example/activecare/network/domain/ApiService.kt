package com.example.activecare.network.domain

import com.example.activecare.cache.domain.Cache
import com.example.activecare.dataclasses.FoodRecord
import com.example.activecare.dataclasses.GetTokens
import com.example.activecare.dataclasses.Limitation
import com.example.activecare.dataclasses.LoginJson
import com.example.activecare.dataclasses.PersonStatistic
import com.example.activecare.dataclasses.Stat
import com.example.activecare.dataclasses.User
import com.example.activecare.dataclasses.WatchStat
import com.example.activecare.dataclasses.Workout
import com.example.activecare.dataclasses.WorkoutStatistic
import com.example.activecare.network.data.ApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface ApiService {
    /**
     * A function that can be used to send a request with an email and password to
     * the server to authenticate the user
     * @param loginData LoginJson dataclass with 2 fields, email and password
     * @return Pair<GetTokens?, Error?> If the user's data is correct, the data class
     * GetTokens will be returned, with two Tokens inside (access and refresh,
     * which must be saved in SharedPref). If an error occurs, the error
     * will be processed and the error details will be returned.
     */
    suspend fun login(loginData: LoginJson): Pair<GetTokens?, Error?>

    /**
     * The function required to update the access token. Called when the application
     * is launched. Takes access tokens, checks that they are not empty strings,
     * goes to the server for new access and refresh tokens.
     * @return Error? If an error occurs, it returns its message.
     */
    suspend fun updateToken(): Error?

    /**
     * A function that can be used to send a request with all the data entered by the
     * user at the onboard and signup stage to the server for user registration.
     * @param user User - all userdata from onboard and signup stages
     * @return Pair<GetTokens?, Error?> - If the user's data is correct, the data class
     * GetTokens will be returned, with two Tokens inside (access and refresh,
     * which must be saved in SharedPref). If an error occurs, the error
     * will be processed and the error details will be returned.
     */
    suspend fun createUser(user: User): Pair<GetTokens?, Error?>

    /**
     * Below is a block of functions for adding and receiving information to/from the database.
     */

    /**
     * The function of adding information received from the watch.
     * @param: data All the information that could be extracted from a wristwatch
     * @return: Pair<WatchStat?, Error?> - If the information is successfully added,
     * the added record and null will be returned, otherwise null, Error
     */
    suspend fun appendUserData(data: WatchStat): Pair<WatchStat?, Error?>
    /**
     * The function of adding information received from the user.
     * @param: data All the information that user want to add about food record
     * @return: Pair<FoodRecord?, Error?> - If the information is successfully added,
     * the added record and null will be returned, otherwise null, Error
     */
    suspend fun appendUserData(data: FoodRecord): Pair<FoodRecord?, Error?>
    /**
     * The function of adding information received from the user.
     * @param: data All the information that extracted from workout
     * @return: Pair<Workout?, Error?> - If the information is successfully added,
     * the added record and null will be returned, otherwise null, Error
     */
    suspend fun appendUserData(data: Workout): Pair<Workout?, Error?>
    /**
     * The function of adding information received from the user.
     * @param: data All the information that user want to add about common statistic.
     * @return: Pair<Stat?, Error?> - If the information is successfully added,
     * the added record and null will be returned, otherwise (null, Error)
     */
    suspend fun appendUserData(data: Stat): Pair<Stat?, Error?>

    suspend fun getUserWatchStat(limit: Limitation): Pair<List<WatchStat>, Error?>
    suspend fun getUserFoodRecords(limit: Limitation): Pair<List<FoodRecord>, Error?>
    suspend fun getUserWorkouts(limit: Limitation): Pair<List<Workout>, Error?>
    suspend fun getUserStat(limit: Limitation): Pair<List<Stat>, Error?>

    suspend fun getStatActivityAndMeasure(date: String): Pair<PersonStatistic, Error?>
    suspend fun getWorkoutActivityAndMeasure(date: String): Pair<WorkoutStatistic, Error?>

    companion object {
        fun create(cache: Cache): ApiService {
            return ApiServiceImpl(
                client = HttpClient(Android) {
                    // JSON
                    install(ContentNegotiation) {
                        json(
                            Json {
                                ignoreUnknownKeys = true
                                isLenient = true
                                encodeDefaults = false
                            }
                        )
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
    }

}