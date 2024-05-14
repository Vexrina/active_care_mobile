package com.example.activecare.common

import android.content.Context
import com.example.activecare.cache.data.CacheImpl
import com.example.activecare.cache.domain.Cache

object CacheProvider {
    private lateinit var cache: Cache

    fun init(context: Context) {
        cache = CacheImpl(context)
    }

    fun getCache(): Cache {
        return cache
    }
}