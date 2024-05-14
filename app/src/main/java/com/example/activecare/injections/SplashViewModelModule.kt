package com.example.activecare.injections

import com.example.activecare.cache.domain.Cache
import com.example.activecare.network.domain.ApiService
import com.example.activecare.screens.splash.SplashViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SplashViewModelModule {
    @Provides
    fun provideSplashViewModel(
        apiService: ApiService,
        cache: Cache,
    ): SplashViewModel {
        return SplashViewModel(apiService = apiService, cache = cache)
    }
}