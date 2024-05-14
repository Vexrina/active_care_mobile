package com.example.activecare.cache.data

import android.content.Context
import com.example.activecare.cache.domain.Cache
import com.example.activecare.dataclasses.OnboardData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheImpl @Inject constructor(
    val context: Context,
) : Cache {
    private val PREFS_NAME = "ActiveCarePrefsFile"
    private val KEY_ACCESS_TOKEN = "user_email"
    private val KEY_REFRESH_TOKEN = "user_pword"
    private val KEY_ONBOARD_GENDER = "user_gender"
    private val KEY_ONBOARD_WEIGHT = "user_weight"
    private val KEY_ONBOARD_HEIGHT = "user_height"
    private val KEY_ONBOARD_BIRTHD = "user_birthd"

    /**
     * Set onboarding data for transfer to sign up screen
     * @param data Data for transfer
     */
    override fun setOnboardData(data: OnboardData) {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putBoolean(KEY_ONBOARD_GENDER, data.gender!!)
        editor.putFloat(KEY_ONBOARD_WEIGHT, data.weight!!.toFloat())
        editor.putFloat(KEY_ONBOARD_HEIGHT, data.height!!.toFloat())
        editor.putString(KEY_ONBOARD_BIRTHD, data.birthDate)
        editor.apply()
    }

    /**
     * Get onboard data in signup screen. Delete all data for save storage
     * @return OnboardData
     */
    override fun getOnboardData(): OnboardData {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val data = OnboardData(
            gender = prefs.getBoolean(KEY_ONBOARD_GENDER, false),
            weight = prefs.getFloat(KEY_ONBOARD_WEIGHT, 0.0F),
            height = prefs.getFloat(KEY_ONBOARD_HEIGHT, 0.0F),
            birthDate = prefs.getString(KEY_ONBOARD_BIRTHD, "")
        )
        val editor = prefs.edit()
        editor.remove(KEY_ONBOARD_GENDER)
        editor.remove(KEY_ONBOARD_WEIGHT)
        editor.remove(KEY_ONBOARD_HEIGHT)
        editor.remove(KEY_ONBOARD_BIRTHD)
        editor.apply()
        return data
    }

    /**
     * Delete user's data from prefs.
     */
    override fun userSignOut() {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.remove(KEY_ACCESS_TOKEN)
        editor.remove(KEY_REFRESH_TOKEN)
        editor.apply()
    }

    /**
     * Put user access and refresh tokens to SharedPreferences
     * @param access user's access token
     * @param refresh user's refresh token
     */
    override fun userSignIn(access: String, refresh: String) {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_ACCESS_TOKEN, access)
        editor.putString(KEY_REFRESH_TOKEN, refresh)
        editor.apply()
    }

    /**
     * Get users data from SharedPreferences
     * @return Pair(access, refresh) access and refresh tokens. Access token can be expired.
     */
    override fun getUsersData(): Pair<String?, String?> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val access = prefs.getString(KEY_ACCESS_TOKEN, "")
        val refresh = prefs.getString(KEY_REFRESH_TOKEN, "")
        return Pair(access, refresh)
    }

    /**
     * Refresh access token
     * @param access new user's access token
     */
    override fun userRefreshToken(access: String) {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_ACCESS_TOKEN, access)
        editor.apply()
    }
}