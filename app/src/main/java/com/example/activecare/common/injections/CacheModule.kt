package com.example.activecare.common.injections

import com.example.activecare.common.CacheProvider
import com.example.activecare.common.cache.domain.Cache
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