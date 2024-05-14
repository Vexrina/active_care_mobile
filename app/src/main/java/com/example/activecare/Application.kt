package com.example.activecare

import android.app.Application
import com.example.activecare.common.CacheProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ActiveCareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CacheProvider.init(this)
        _instance = this
    }

    companion object {
        private var _instance: ActiveCareApplication? = null
        val instance get() = requireNotNull(_instance)
    }
}