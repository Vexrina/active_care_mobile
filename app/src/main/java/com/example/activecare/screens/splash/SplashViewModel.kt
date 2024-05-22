package com.example.activecare.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activecare.cache.domain.Cache
import com.example.activecare.navigation.NavigationTree
import com.example.activecare.network.domain.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private  val apiService: ApiService,
    private  val cache: Cache,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var _nextScreen = MutableStateFlow(NavigationTree.Home.name)
    var nextScreen: StateFlow<String> = _nextScreen

    init {
        viewModelScope.launch {
            if (!checkTokens()){
                _nextScreen.value = NavigationTree.Onboard.name
                _isLoading.value = false
                return@launch
            }
            checkConnection()
            _isLoading.value = false
        }
    }

    private fun checkTokens() : Boolean {
        Log.d("SVM", "checking tokens")
        val tokens = cache.getUsersData()
        return !(tokens.first==null || tokens.second == null)
    }

    private suspend fun checkConnection() {
        val err = apiService.updateToken()
        if (err==null) {
            _nextScreen.value = NavigationTree.Home.name
        } else {
            _nextScreen.value = when (err){
                Error("Internal Server Error") -> NavigationTree.NetworkError.name
                else -> NavigationTree.Onboard.name
            }
        }
    }
}