package com.example.activecare.injections

import com.example.activecare.cache.domain.Cache
import com.example.activecare.network.domain.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    fun provideApiService(
        cache: Cache,
    ): ApiService {
        return ApiService.create(cache = cache)
    }
}