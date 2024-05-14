package com.example.activecare.network.data

import android.util.Log
import com.example.activecare.cache.domain.Cache
import com.example.activecare.dataclasses.FoodRecord
import com.example.activecare.dataclasses.GetTokens
import com.example.activecare.dataclasses.LoginJson
import com.example.activecare.dataclasses.Token
import com.example.activecare.dataclasses.User
import com.example.activecare.dataclasses.WatchStat
import com.example.activecare.dataclasses.Workout
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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiServiceImpl @Inject constructor(
    private val client: HttpClient,
    private val cache: Cache,
) : ApiService {
    override suspend fun login(loginData: LoginJson): Pair<List<Token>?, Error?> {
        return try {
            val response = client.post {
                header("Bearer-Authorization", "")
                url(ApiRoutes.LOGIN)
                contentType(ContentType.Application.Json)
                setBody(loginData)
            }

            Pair(response.body<List<Token>>(), null)
        } catch (ex: Exception) {
            Pair(null, catchRequestsErrors(ex))
        }
    }

    override suspend fun createUser(user: User): Pair<GetTokens?, Error?> {
        return try {
            val response = client.post {
                url(ApiRoutes.REGISTER)
                contentType(ContentType.Application.Json)
                setBody(user)
            }
            when (response.status) {
                HttpStatusCode.OK -> Pair(response.body<GetTokens>(), null)
                HttpStatusCode.BadRequest -> Pair(null, Error("Email is not valid"))
                HttpStatusCode.Conflict -> Pair(null, Error("User already exist"))
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
                header("Bearer-Authorization", tokens.second)
                url(ApiRoutes.REFRESH_TOKEN)
                contentType(ContentType.Application.Json)
            }
            val newTokens = response.body<List<Token>>()
            cache.userSignIn(newTokens[0].access_token, newTokens[1].access_token)
            null
        } catch (ex: Exception) {
            catchRequestsErrors(ex)
        }
    }

    override suspend fun appendUserData(data: WatchStat): Pair<WatchStat?, Error?> {
        return try {
            val response = client.post {
                url(ApiRoutes.APPEND_WATCH_STAT)
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
            val response = client.post {
                url(ApiRoutes.APPEND_FOOD_RECORD)
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
            val response = client.post {
                url(ApiRoutes.APPEND_WORKOUT)
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

    override suspend fun getUserData(userId: String, dataType: String): Pair<Any?, Error?> {
        return when (dataType) {
            "foodRecord" -> getData(ApiRoutes.GET_FOOD_RECORD + userId)
            "watchStat" -> getData(ApiRoutes.GET_WATCH_STAT + userId)
            "workout" -> getData(ApiRoutes.GET_WORKOUT + userId)
            "stat" -> getData(ApiRoutes.GET_STATS + userId)
            else -> getData(ApiRoutes.GET_STATS + userId)
        }
    }

    override suspend fun getData(url: String): Pair<Any?, Error?> {
        return try {
            val response = client.get {
                header("Bearer-Authorization", "")
                url(url)
                contentType(ContentType.Application.Json)
            }
            Pair(response.body(), null)
        } catch (ex: RedirectResponseException) {
            // 3xx responses
            Log.e("ApiError", ex.response.status.description)
            Pair(null, Error(ex.response.status.description))
        } catch (ex: ClientRequestException) {
            // 4xx responses
            Log.e("ApiError", ex.response.status.description)
            Pair(null, Error(ex.response.status.description))
        } catch (ex: ServerResponseException) {
            // 5xx responses
            Log.e("ApiError", ex.response.status.description)
            Pair(null, Error(ex.response.status.description))
        }
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