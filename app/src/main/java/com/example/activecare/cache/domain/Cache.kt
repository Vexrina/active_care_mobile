package com.example.activecare.cache.domain

import com.example.activecare.dataclasses.OnboardData

interface Cache {
    fun setOnboardData(data: OnboardData)
    fun getOnboardData(): OnboardData
    fun userSignOut()
    fun userSignIn(access: String, refresh: String)
    fun getUsersData(): Pair<String?, String?>
    fun userRefreshToken(access: String)
}