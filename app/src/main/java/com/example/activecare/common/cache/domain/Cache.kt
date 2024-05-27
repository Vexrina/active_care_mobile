package com.example.activecare.common.cache.domain

import com.example.activecare.common.dataclasses.OnboardData

interface Cache {
    /**
     * Set onboarding data for transfer to sign up screen
     * @param data Data for transfer
     */
    fun setOnboardData(data: OnboardData)
    /**
     * Get onboard data in signup screen.
     * @return OnboardData
     */
    fun getOnboardData(): OnboardData

    /**
     * Delete onboard data for save storage
     */
    fun deleteOnboardData()
    /**
     * Delete user's data from prefs.
     */
    fun userSignOut()
    /**
     * Put user access and refresh tokens to SharedPreferences
     * @param access user's access token
     * @param refresh user's refresh token
     */
    fun userSignIn(access: String, refresh: String)
    /**
     * Get users data from SharedPreferences
     * @return Pair(access, refresh) access and refresh tokens. Access token can be expired.
     */
    fun getUsersData(): Pair<String?, String?>


    fun setTotalSteps(steps: Int, date: String)
    fun setTodaySteps(steps: Int)
    fun getTodaySteps(): Int
    fun getTotalSteps(): Int
    fun getLastUpdate(): String?
}