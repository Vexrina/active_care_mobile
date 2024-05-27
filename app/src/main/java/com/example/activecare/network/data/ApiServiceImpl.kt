package com.example.activecare.network.data

import android.util.Log
import com.example.activecare.common.averageInStats
import com.example.activecare.common.cache.domain.Cache
import com.example.activecare.common.dataclasses.ActivityStat
import com.example.activecare.common.dataclasses.ActivityWorkout
import com.example.activecare.common.dataclasses.FoodRecord
import com.example.activecare.common.dataclasses.GetTokens
import com.example.activecare.common.dataclasses.Limitation
import com.example.activecare.common.dataclasses.LoginJson
import com.example.activecare.common.dataclasses.MeasureStat
import com.example.activecare.common.dataclasses.MeasureWorkout
import com.example.activecare.common.dataclasses.PersonStatistic
import com.example.activecare.common.dataclasses.Stat
import com.example.activecare.common.dataclasses.User
import com.example.activecare.common.dataclasses.WatchStat
import com.example.activecare.common.dataclasses.Workout
import com.example.activecare.common.dataclasses.WorkoutStatistic
import com.example.activecare.common.filterWorkoutsByWorkoutTypeAndCalculateSum
import com.example.activecare.network.domain.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val cache: Cache,
) : ApiService {


    override suspend fun login(loginData: LoginJson): Pair<GetTokens?, Error?> {
        return try {
            val response = client.post {
                header("Bearer-Authorization", "")
                url(ApiRoutes.LOGIN)
                contentType(ContentType.Application.Json)
                setBody(loginData)
            }

            Pair(response.body<GetTokens>(), null)
        } catch (ex: Exception) {
            Pair(null, catchRequestsErrors(ex))
        }
    }

    override suspend fun createUser(user: User): Pair<GetTokens?, Error?> {
        return try {
            Log.d("apiService", user.toString())
            val jsonBody = Json.encodeToString(user)
            Log.d("apiService", jsonBody) // Выводим JSON в лог
            val response = client.post {
                url(ApiRoutes.REGISTER)
                contentType(ContentType.Application.Json)
                setBody(user)
            }
            when (response.status) {
                HttpStatusCode.OK -> Pair(response.body<GetTokens>(), null)
                HttpStatusCode.BadRequest -> Pair(null, Error("Email is not valid"))
                HttpStatusCode.Conflict -> Pair(null, Error("User already exist"))
                HttpStatusCode.UnprocessableEntity -> Pair(null, Error("some info was empty"))
                else -> Pair(null, Error("Something went wrong"))
            }
        } catch (ex: Exception) {
            Log.d("apiService", "here")
            Pair(null, Error("Something went wrong"))
        }
    }

    override suspend fun updateToken(): Error? {
        val tokens = cache.getUsersData()
        if (tokens.first == "" || tokens.second == "") {
            return Error("No tokens in SharedPreferences")
        }
        return try {
            val response = client.get {
                header("Authorization", "Bearer ${tokens.second}")
                url(ApiRoutes.REFRESH_TOKEN)
                contentType(ContentType.Application.Json)
            }
            val newTokens = response.body<GetTokens>()
            cache.userSignIn(
                newTokens.access_token.access_token,
                newTokens.refresh_token.access_token
            )
            null
        } catch (ex: Exception) {
            catchRequestsErrors(ex)
        }
    }

    override suspend fun appendUserData(data: WatchStat): Pair<WatchStat?, Error?> {
        return try {
            val tokens = cache.getUsersData()
            if (tokens.first == "" || tokens.second == "") {
                return Pair(null, Error("No tokens in SharedPreferences"))
            }
            val response = client.post {
                header("Authorization", "Bearer ${tokens.first}")
                url(ApiRoutes.WATCH_STAT)
                contentType(ContentType.Application.Json)
                setBody(data)
            }
            when (response.status) {
                HttpStatusCode.OK -> Pair(response.body<WatchStat>(), null)
                HttpStatusCode.BadRequest -> Pair(null, Error("Email is not valid"))
                HttpStatusCode.Conflict -> Pair(null, Error("User already exist"))
                else -> Pair(null, Error("Something went wrong"))
            }
        } catch (ex: Exception) {
            Pair(null, Error("Something went wrong"))
        }
    }

    override suspend fun appendUserData(data: FoodRecord): Pair<FoodRecord?, Error?> {
        return try {
            val tokens = cache.getUsersData()
            if (tokens.first == "" || tokens.second == "") {
                return Pair(null, Error("No tokens in SharedPreferences"))
            }
            val response = client.post {
                header("Authorization", "Bearer ${tokens.first}")
                url(ApiRoutes.FOOD_RECORD)
                contentType(ContentType.Application.Json)
                setBody(data)
            }
            when (response.status) {
                HttpStatusCode.OK -> Pair(response.body<FoodRecord>(), null)
                HttpStatusCode.BadRequest -> Pair(null, Error("Email is not valid"))
                HttpStatusCode.Conflict -> Pair(null, Error("User already exist"))
                else -> Pair(null, Error("Something went wrong"))
            }
        } catch (ex: Exception) {
            Pair(null, Error("Something went wrong"))
        }
    }

    override suspend fun appendUserData(data: Workout): Pair<Workout?, Error?> {
        return try {
            val tokens = cache.getUsersData()
            if (tokens.first == "" || tokens.second == "") {
                return Pair(null, Error("No tokens in SharedPreferences"))
            }
            val response = client.post {
                header("Authorization", "Bearer ${tokens.first}")
                url(ApiRoutes.WORKOUT)
                contentType(ContentType.Application.Json)
                setBody(data)
            }
            when (response.status) {
                HttpStatusCode.OK -> Pair(response.body<Workout>(), null)
                HttpStatusCode.BadRequest -> Pair(null, Error("Email is not valid"))
                HttpStatusCode.Conflict -> Pair(null, Error("User already exist"))
                else -> Pair(null, Error("Something went wrong"))
            }
        } catch (ex: Exception) {
            Pair(null, Error("Something went wrong"))
        }
    }

    override suspend fun appendUserData(data: Stat): Pair<Stat?, Error?> {
        return try {
            val tokens = cache.getUsersData()
            if (tokens.first == "" || tokens.second == "") {
                return Pair(null, Error("No tokens in SharedPreferences"))
            }
            val response = client.post {
                header("Authorization", "Bearer ${tokens.first}")
                url(ApiRoutes.STATS)
                contentType(ContentType.Application.Json)
                setBody(data)
            }
            when (response.status) {
                HttpStatusCode.OK -> Pair(response.body<Stat>(), null)
                HttpStatusCode.BadRequest -> Pair(null, Error("Email is not valid"))
                HttpStatusCode.Conflict -> Pair(null, Error("User already exist"))
                else -> Pair(null, Error("Something went wrong"))
            }
        } catch (ex: Exception) {
            Pair(null, Error("Something went wrong"))
        }
    }


    override suspend fun getUserWatchStat(limit: Limitation): Pair<List<WatchStat>, Error?> {
        return try {
            val tokens = cache.getUsersData()
            if (tokens.first == "" || tokens.second == "") {
                return Pair(emptyList(), Error("No tokens in SharedPreferences"))
            }
            val response = client.get {
                header("Authorization", "Bearer ${tokens.first}")
                url(createGetUrl(limit, ApiRoutes.WATCH_STAT))
                contentType(ContentType.Application.Json)
            }
            Pair(response.body<List<WatchStat>>(), null)
        } catch (ex: Exception) {
            Pair(emptyList(), catchRequestsErrors(ex))
        }
    }

    override suspend fun getUserFoodRecords(limit: Limitation): Pair<List<FoodRecord>, Error?> {
        return try {
            val tokens = cache.getUsersData()
            if (tokens.first == "" || tokens.second == "") {
                return Pair(emptyList(), Error("No tokens in SharedPreferences"))
            }
            val response = client.get {
                header("Authorization", "Bearer ${tokens.first}")
                url(createGetUrl(limit, ApiRoutes.FOOD_RECORD))
                contentType(ContentType.Application.Json)
            }
            Pair(response.body<List<FoodRecord>>(), null)
        } catch (ex: Exception) {
            Pair(emptyList(), catchRequestsErrors(ex))
        }
    }

    override suspend fun getUserWorkouts(limit: Limitation): Pair<List<Workout>, Error?> {
        return try {
            val tokens = cache.getUsersData()
            if (tokens.first == "" || tokens.second == "") {
                return Pair(emptyList(), Error("No tokens in SharedPreferences"))
            }
            val response = client.get {
                header("Authorization", "Bearer ${tokens.first}")
                url(createGetUrl(limit, ApiRoutes.WORKOUT))
                contentType(ContentType.Application.Json)
            }
            Pair(response.body<List<Workout>>(), null)
        } catch (ex: Exception) {
            Pair(emptyList(), catchRequestsErrors(ex))
        }
    }

    override suspend fun getUserStat(limit: Limitation): Pair<List<Stat>, Error?> {
        return try {
            val tokens = cache.getUsersData()
            if (tokens.first == "" || tokens.second == "") {
                return Pair(emptyList(), Error("No tokens in SharedPreferences"))
            }
            val response = client.get {
                header("Authorization", "Bearer ${tokens.first}")
                url(createGetUrl(limit, ApiRoutes.STATS))
                contentType(ContentType.Application.Json)
            }
            Pair(response.body<List<Stat>>(), null)
        } catch (ex: Exception) {
            Pair(emptyList(), catchRequestsErrors(ex))
        }
    }

    override suspend fun getUserWeight(): Pair<Float?, Error?> {
        return try {
            val tokens = cache.getUsersData()
            if (tokens.first == "" || tokens.second == "") {
                return Pair(null, Error("No tokens in SharedPreferences"))
            }
            val response = client.get {
                header("Authorization", "Bearer ${tokens.first}")
                url(ApiRoutes.USER)
                contentType(ContentType.Application.Json)
            }
            val weight = response.body<User>().weight
            Pair(weight, null)
        } catch (ex: Exception) {
            Pair(null, catchRequestsErrors(ex))
        }
    }

    override suspend fun getUser(): Pair<User?, Error?> {
        return try {
            val tokens = cache.getUsersData()
            if (tokens.first == "" || tokens.second == "") {
                return Pair(null, Error("No tokens in SharedPreferences"))
            }
            val response = client.get {
                header("Authorization", "Bearer ${tokens.first}")
                url(ApiRoutes.USER)
                contentType(ContentType.Application.Json)
            }
            Pair(response.body<User>(), null)
        } catch (ex: Exception) {
            Pair(null, catchRequestsErrors(ex))
        }
    }

    override suspend fun getStatActivityAndMeasure(date: String): Pair<PersonStatistic, Error?> {
        val nullableStat = PersonStatistic(
            activity = ActivityStat(),
            measure = MeasureStat()
        )
        try {
            val tokens = cache.getUsersData()
            if (tokens.first == "" || tokens.second == "") {
                return Pair(nullableStat, Error("No tokens in SharedPreferences"))
            }
            val limit = Limitation(
                date = date + "T23:59:59",
                date_offset = 1
            )
            val response1 = getUserStat(limit)
            if (response1.second != null) {
                return Pair(nullableStat, response1.second)
            }
            val response2 = getUserFoodRecords(limit)
            if (response2.second != null) {
                return Pair(nullableStat, response2.second)
            }
            val response3 = getUserWorkouts(limit)
            if (response3.second != null) {
                return Pair(nullableStat, response3.second)
            }
            val activityStat = ActivityStat(
                date_stamp = date,
                steps = if (response1.first.isNotEmpty()) response1.first[0].steps else 0,
                distance = response3.first.sumOf {
                    it.distance.toDouble()
                }.toFloat(),
                calories = response3.first.sumOf {
                    it.burned_calories
                }
            )
            val avg = averageInStats(response1.first)
            val measureStat = MeasureStat(
                date_stamp = date,
                weight = if (response1.first.isNotEmpty()) response1.first[0].weight else 0f,
                sleep = if (response1.first.isNotEmpty()) response1.first[0].sleep else 0,
                pulse = avg.first.toInt(),
                spO2 = avg.second.toInt(),
                calories = response2.first.sumOf {
                    it.calories
                }
            )
            return Pair(
                PersonStatistic(
                    activityStat,
                    measureStat
                ), null
            )
        } catch (ex: Exception) {
            return Pair(
                nullableStat,
                catchRequestsErrors(ex)
            )
        }
    }

    override suspend fun getWorkoutActivityAndMeasure(date: String): Pair<WorkoutStatistic, Error?> {
        val nullableStat = WorkoutStatistic(
            activity = ActivityWorkout(),
            measure = MeasureWorkout()
        )
        try {
            val tokens = cache.getUsersData()
            if (tokens.first == "" || tokens.second == "") {
                return Pair(nullableStat, Error("No tokens in SharedPreferences"))
            }
            val limit = Limitation(
                date = date + "T23:59:59",
                date_offset = 1,
            )
            val response1 = getUserStat(limit)
            if (response1.second != null) {
                return Pair(nullableStat, response1.second)
            }

            val response2 = getUserWorkouts(limit)
            if (response2.second != null) {
                return Pair(nullableStat, response2.second)
            }

            val activityWorkout = ActivityWorkout(
                date_stamp = date,
                totalDistance = response2.first.sumOf { it.distance.toDouble() }.toFloat(),
                streetRun = filterWorkoutsByWorkoutTypeAndCalculateSum(response2.first, 1),
                trackRun = filterWorkoutsByWorkoutTypeAndCalculateSum(response2.first, 0),
                walking = filterWorkoutsByWorkoutTypeAndCalculateSum(response2.first, 2),
                bike = filterWorkoutsByWorkoutTypeAndCalculateSum(response2.first, 3),
            )
            val avg = averageInStats(response1.first)
            val measureWorkout = MeasureWorkout(
                date_stamp = date,
                pulse = avg.first.toInt(),
                spO2 = avg.second.toInt(),
            )

            return Pair(
                WorkoutStatistic(
                    activityWorkout,
                    measureWorkout
                ), null
            )
        } catch (ex: Exception) {
            return Pair(
                nullableStat,
                catchRequestsErrors(ex)
            )
        }
    }

    /**
     * The function is a utility that helps you create a url to
     * access the server using query parameters
     * @param limit By themselves, query parameters that are inserted into the url
     * @param url The endpoint that needs query parameters
     * @return The URL where you can go with a JWT token and get the necessary data
     * in the required range.
     */
    private fun createGetUrl(limit: Limitation, url: String): String {
        return url + "?date=${limit.date}&offset=${limit.date_offset}&deltatype=${limit.deltatype}"
    }

    /**
     * The function is a utility that helps to parse the error that occurred when
     * sending the request. This function redirects the error to another function,
     * according to the beginning of the status code.
     * @param ex The error that occurred
     * @return The error that we parsed
     */
    private fun catchRequestsErrors(ex: Exception): Error {
        return when (ex) {
            is RedirectResponseException -> catch3Errors(ex)
            is ClientRequestException -> catch4Errors(ex)
            is ServerResponseException -> catch5Errors(ex)
            else -> catchAnotherErrors(ex)
        }
    }

    /**
     * Catch all Client errors, 3xx codes
     * @param ex The error that occurred
     * @return The error that we parsed
     */
    private fun catch3Errors(ex: RedirectResponseException): Error {
        Log.e("ApiError", ex.response.status.description)
        return when (ex.response.status.value) {
            300 -> Error("Multiple Choices")
            301 -> Error("Moved Permanently")
            else -> Error(ex.response.status.description)
        }
    }


    /**
     * Catch all Client errors, 4xx codes
     * @param ex The error that occurred
     * @return The error that we parsed
     */
    private fun catch4Errors(ex: ClientRequestException): Error {
        Log.e("ApiError", ex.response.status.description)
        return when (ex.response.status.value) {
            400 -> Error("Bad request")
            401 -> Error("Incorrect email or password or token expired")
            405 -> Error("Method not allowed")
            422 -> Error("Bad data")
            else -> Error("Something wrong with ur data")
        }
    }

    /**
     * Catch all Client errors, 5xx codes
     * @param ex The error that occurred
     * @return The error that we parsed
     */
    private fun catch5Errors(ex: ServerResponseException): Error {
        Log.e("ApiError", ex.response.status.description)
        return when (ex.response.status.value) {
            500 -> Error("Internal Server Error")
            else -> Error("Something went wrong")
        }
    }

    /**
     * Catch all another errors, that can be.
     * @param ex The error that occurred
     * @return The error that we parsed
     */
    private fun catchAnotherErrors(ex: Exception): Error {
        Log.e("ApiError", "Unexpected error: ${ex.message}")
        return Error(ex.message)
    }
}