package com.example.activecare.common.cache.data

import android.content.Context
import com.example.activecare.common.cache.domain.Cache
import com.example.activecare.common.dataclasses.OnboardData
import com.example.activecare.common.getCurrentDate
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

    // For step sensor
    private val KEY_TOTAL_STEPS = "total_steps"
    private val KEY_DATE_UPDATE = "last_update"
    private val KEY_TODAY_STEPS = "today_steps"

    override fun setOnboardData(data: OnboardData) {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putBoolean(KEY_ONBOARD_GENDER, data.gender!!)
        editor.putFloat(KEY_ONBOARD_WEIGHT, data.weight!!.toFloat())
        editor.putFloat(KEY_ONBOARD_HEIGHT, data.height!!.toFloat())
        editor.putString(KEY_ONBOARD_BIRTHD, data.birthDate)
        editor.apply()
    }

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

    override fun userSignOut() {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.remove(KEY_ACCESS_TOKEN)
        editor.remove(KEY_REFRESH_TOKEN)
        editor.apply()
    }

    override fun userSignIn(access: String, refresh: String) {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_ACCESS_TOKEN, access)
        editor.putString(KEY_REFRESH_TOKEN, refresh)
        editor.apply()
    }

    override fun getUsersData(): Pair<String?, String?> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val access = prefs.getString(KEY_ACCESS_TOKEN, "")
        val refresh = prefs.getString(KEY_REFRESH_TOKEN, "")
        return Pair(access, refresh)
    }


    override fun setTotalSteps(steps: Int, date: String) {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putInt(KEY_TOTAL_STEPS, steps)
        editor.putString(KEY_DATE_UPDATE, date)
        editor.apply()
    }

    override fun setTodaySteps(steps: Int) {
        val editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        editor.putInt(KEY_TODAY_STEPS, steps)
        editor.putString(KEY_DATE_UPDATE, getCurrentDate())
        editor.apply()
    }

    override fun getTodaySteps(): Int {
        return context
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_TODAY_STEPS, 0)
    }

    override fun getTotalSteps(): Int {
        return context
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_TOTAL_STEPS, 0)
    }

    override fun getLastUpdate(): String? {
        return context
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_DATE_UPDATE, "")
    }
}