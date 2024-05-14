package com.example.activecare.injections

import com.example.activecare.cache.domain.Cache
import com.example.activecare.common.CacheProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    fun provideCache(): Cache {
        return CacheProvider.getCache()
    }
}