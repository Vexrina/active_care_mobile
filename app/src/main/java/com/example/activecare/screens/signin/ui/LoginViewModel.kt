package com.example.activecare.screens.signin.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activecare.common.cache.domain.Cache
import com.example.activecare.common.CacheProvider
import com.example.activecare.common.EventHandler
import com.example.activecare.common.dataclasses.LoginJson
import com.example.activecare.common.dataclasses.EventTuple
import com.example.activecare.common.dataclasses.User
import com.example.activecare.network.domain.ApiService
import com.example.activecare.screens.signin.models.SignInEvent
import com.example.activecare.screens.signin.models.SignInSubState
import com.example.activecare.screens.signin.models.SignInViewState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = LoginViewModel.LoginViewModelFactory::class)
class LoginViewModel @AssistedInject constructor(
    @Assisted signIn: Boolean,
    @Assisted val apiService: ApiService,
) : ViewModel(), EventHandler<SignInEvent> {
    @AssistedFactory
    interface LoginViewModelFactory {
        fun create(
            signIn: Boolean,
            apiService: ApiService,
        ): LoginViewModel
    }

    private val cache: Cache = CacheProvider.getCache()

    private val _viewState: MutableStateFlow<SignInViewState> = MutableStateFlow(
        SignInViewState(
            signInSubState = if (signIn) SignInSubState.SignIn else SignInSubState.SignUpStart
        )
    )
    val viewState: StateFlow<SignInViewState> = _viewState

    override fun obtainEvent(event: SignInEvent) {
        when (event) {
            SignInEvent.ActionClicked -> switchActionState()
            SignInEvent.LoginActionInvoked -> loginActionInvoked()
            SignInEvent.SignUpBack -> signUpBackState()
            SignInEvent.ErrorShown -> sendErrorEvent(null)
            SignInEvent.ForgetContinueButtonClicked -> forgetContinueClicked()
            SignInEvent.ForgotBack -> forgetBackClicked()
            is SignInEvent.EmailChanged -> emailChanged(event.value)
            is SignInEvent.PasswordChanged -> passwordChanged(event.value)
            is SignInEvent.ConfirmPasswordChanged -> confirmPasswordChanged(event.value)
            is SignInEvent.ForgetClicked -> forgetClicked()
            is SignInEvent.LoginClicked -> loginClicked()
            is SignInEvent.RegisterClicked -> registerClicked()
            is SignInEvent.UsernameChanged -> usernameChanged(event.value)
            else -> {}
        }
    }

    private fun switchActionState() {
        val currentState = _viewState.value
        val newState = when (currentState.signInSubState) {
            SignInSubState.SignIn -> currentState.copy(signInSubState = SignInSubState.SignUpStart)
            SignInSubState.SignUpStart -> currentState.copy(signInSubState = SignInSubState.SignIn)
            SignInSubState.SignUpEnd -> currentState.copy(signInSubState = SignInSubState.SignIn)
            SignInSubState.ForgotStart -> currentState.copy(signInSubState = SignInSubState.SignUpStart)
            SignInSubState.ForgotEnd -> currentState.copy(signInSubState = SignInSubState.SignUpStart)
        }
        _viewState.value = newState
    }

    private fun signUpBackState() {
        val currentState = _viewState.value
        val newState = when (currentState.signInSubState) {
            SignInSubState.SignUpEnd -> currentState.copy(signInSubState = SignInSubState.SignUpStart)
            else -> currentState
        }
        _viewState.value = newState
    }


    private fun usernameChanged(value: String) {
        val currentState = _viewState.value
        val newState = currentState.copy(
            usernameValue = value.filter {
                it.isLetter()
            }
        )
        _viewState.value = newState
    }

    private fun emailChanged(value: String) {
        val currentState = _viewState.value
        val newState = currentState.copy(emailValue = value)
        _viewState.value = newState
    }

    private fun passwordChanged(value: String) {
        val currentState = _viewState.value
        val newState = currentState.copy(passwordValue = value)
        _viewState.value = newState
    }

    private fun confirmPasswordChanged(value: String) {
        val currentState = _viewState.value
        val newState = currentState.copy(confirmPasswordValue = value)
        _viewState.value = newState
    }

    private fun forgetBackClicked() {
        val currentState = _viewState.value
        val newState = when (currentState.signInSubState) {
            SignInSubState.ForgotStart -> currentState.copy(signInSubState = SignInSubState.SignIn)
            SignInSubState.ForgotEnd -> currentState.copy(signInSubState = SignInSubState.ForgotStart)
            else -> currentState.copy(signInSubState = SignInSubState.ForgotStart)
        }
        _viewState.value = newState
    }

    private fun forgetContinueClicked() {
        val currentState = _viewState.value
        val newState = currentState.copy(signInSubState = SignInSubState.ForgotEnd)
        _viewState.value = newState
        // TODO: add logic to send letter to user email
    }

    private fun forgetClicked() {
        val currentState = _viewState.value
        val newState = currentState.copy(signInSubState = SignInSubState.ForgotStart)
        _viewState.value = newState
    }

    private fun sendErrorEvent(errorMessage: String?) {
        if (errorMessage != null) {
            viewModelScope.launch {
                _viewState.value.eventChannel.send(
                    EventTuple(
                        SignInEvent = SignInEvent.ErrorShown,
                        Message = errorMessage,
                    )
                )
            }
        }
    }

    private fun sendSuccessEvent() {
        viewModelScope.launch {
            _viewState.value.eventChannel.send(
                EventTuple(
                    SignInEvent = SignInEvent.ActionClicked,
                    Message = "",
                )
            )
        }
    }

    private fun registerClicked() {
        val currentState = _viewState.value
        val newState = if (currentState.signInSubState == SignInSubState.SignUpStart) {
            SignInSubState.SignUpEnd
        } else {
            performRegister()
            currentState.signInSubState
        }
        val updatedState = currentState.copy(signInSubState = newState)

        _viewState.value = updatedState
    }

    private fun performRegister() {
        viewModelScope.launch {
            val username = _viewState.value.usernameValue
            val email = _viewState.value.emailValue
            val password = _viewState.value.passwordValue
            val confirmPassword = _viewState.value.confirmPasswordValue
            if (
                username.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                confirmPassword.isEmpty()
            ) {
                sendErrorEvent("Some fields is empty")
                return@launch
            }
            if (confirmPassword != password) {
                sendErrorEvent("Passwords is not equals")
                return@launch
            }
            if (!isValidEmail(email)) {
                sendErrorEvent("Email is not valid")
                return@launch
            }

            val onboardData = cache.getOnboardData()

            val user = User(
                username = username,
                email = email,
                password = password,
                gender = onboardData.gender!!,
                weight = onboardData.weight!!,
                height = onboardData.height!!,
                birthdate = onboardData.birthDate!!,
            )
            Log.d("LVM", user.toString())
            val result = apiService.createUser(user)
            if (result.second != null) {
                sendErrorEvent(result.second!!.message)
                return@launch
            }
            if (result.first == null) {
                sendErrorEvent("Something went wrong")
                return@launch
            }
            val access_token = result.first!!.access_token.access_token
            val refresh_token = result.first!!.refresh_token.access_token
            cache.userSignIn(access_token, refresh_token)
            sendSuccessEvent()
        }
    }

    private fun loginActionInvoked() {}

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[a-z0-9.]+@[a-z0-9]+\\.[a-z]{2,}\$")
        return emailRegex.matches(email)
    }


    private fun loginClicked() {
        viewModelScope.launch {
            val email = _viewState.value.emailValue
            val password = _viewState.value.passwordValue
            if (email.isEmpty() || password.isEmpty()) {
                sendErrorEvent("Some field is empty")
                return@launch
            }
            if (!isValidEmail(email)) {
                Log.d("LVM", "email is not valid")
                sendErrorEvent("Email is not valid")
                return@launch
            }
            try {
                val result = apiService.login(
                    LoginJson(
                        email,
                        password
                    )
                )
                if (result.second != null) {
                    sendErrorEvent(result.second!!.message)
                    return@launch
                }
                if (result.first == null){
                    sendErrorEvent("Something goes wrong")
                    return@launch
                }
                cache.userSignIn(
                    result.first!!.access_token.access_token,
                    result.first!!.refresh_token.access_token
                )
                sendSuccessEvent()
            } catch (ex: JsonConvertException) {
                Log.e("LVM", "${ex.message}")
                val errorDetail = ex.message!!.split("detail\":\"")[1]
                sendErrorEvent(errorDetail)
            }
        }
    }
}