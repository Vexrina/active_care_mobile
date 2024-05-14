package com.example.activecare.screens.onboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activecare.cache.domain.Cache
import com.example.activecare.common.EventHandler
import com.example.activecare.dataclasses.OnboardData
import com.example.activecare.dataclasses.SignInEventTuple
import com.example.activecare.screens.onboard.models.OnboardEvent
import com.example.activecare.screens.onboard.models.OnboardSubState
import com.example.activecare.screens.onboard.models.OnboardViewState
import com.example.activecare.screens.signin.models.SignInEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val cache: Cache,
) : ViewModel(), EventHandler<OnboardEvent> {
    private val _viewState: MutableStateFlow<OnboardViewState> =
        MutableStateFlow(OnboardViewState())
    val viewState: StateFlow<OnboardViewState> = _viewState

    override fun obtainEvent(event: OnboardEvent) {
        when (event) {
            is OnboardEvent.GenderClicked -> genderChosen(event.value)
            OnboardEvent.NextClicked -> nextActionState()
            OnboardEvent.PrevClicked -> prevActionState()
            is OnboardEvent.BirthDateChanged -> birthDateChanged(event.value)
            is OnboardEvent.HeightChanged -> heightChanged(event.value)
            is OnboardEvent.WeightChanged -> weightChanged(event.value)
            OnboardEvent.ToChooseClicked -> toChoose()
            OnboardEvent.SaveToCache -> saveToCache()
        }
    }

    private fun birthDateChanged(value: String) {
        _viewState.update {
            it.copy(
                birthdate = value
            )
        }
    }

    private fun heightChanged(value: String) {
        _viewState.update {
            it.copy(
                height = value
            )
        }
    }

    private fun weightChanged(value: String) {
        _viewState.update {
            it.copy(
                weight = value
            )
        }
    }

    private fun toChoose() {
        val newSubState = OnboardSubState.Choose
        _viewState.update {
            it.copy(
                onboardSubState = newSubState
            )
        }
    }

    private fun nextActionState() {
        val newSubState = when (_viewState.value.onboardSubState) {
            OnboardSubState.Choose -> OnboardSubState.Gender
            OnboardSubState.Gender -> OnboardSubState.Weight
            OnboardSubState.Weight -> OnboardSubState.Height
            OnboardSubState.Height -> OnboardSubState.Age
            OnboardSubState.Age -> OnboardSubState.Gender
        }
        _viewState.update {
            it.copy(
                onboardSubState = newSubState
            )
        }
    }

    private fun prevActionState() {
        val newSubState = when (_viewState.value.onboardSubState) {
            OnboardSubState.Choose -> OnboardSubState.Age
            OnboardSubState.Gender -> OnboardSubState.Choose
            OnboardSubState.Weight -> OnboardSubState.Gender
            OnboardSubState.Height -> OnboardSubState.Weight
            OnboardSubState.Age -> OnboardSubState.Height
        }
        _viewState.update {
            it.copy(
                onboardSubState = newSubState
            )
        }
    }

    private fun genderChosen(value: Boolean) {
        _viewState.update {
            it.copy(
                female = value
            )
        }
    }

    private fun saveToCache(){
        try {
            checkNullable()
            cache.setOnboardData(OnboardData(
                gender = _viewState.value.female,
                weight = _viewState.value.weight!!.toFloat(),
                height = _viewState.value.height!!.toFloat(),
                birthDate = _viewState.value.birthdate,
            ))
            sendSuccessEvent()
        } catch (ex:Exception){
            when (ex){
                is NumberFormatException -> sendErrorEvent("Bad value in some field")
                is NullPointerException -> sendErrorEvent(ex.message!!)
            }
        }
    }

    private fun checkNullable(){
        if (_viewState.value.female == null ||
            _viewState.value.height == null ||
            _viewState.value.weight == null ||
            _viewState.value.birthdate == null){
            throw NullPointerException("Some fields is empty")
        }
    }

    private fun sendErrorEvent(errorMessage: String?) {
        if (errorMessage != null) {
            viewModelScope.launch {
                _viewState.value.eventChannel.send(
                    SignInEventTuple(
                        Event = SignInEvent.ErrorShown,
                        Message = errorMessage,
                    )
                )
            }
        }
    }

    private fun sendSuccessEvent() {
        viewModelScope.launch {
            _viewState.value.eventChannel.send(
                SignInEventTuple(
                    Event = SignInEvent.ActionClicked,
                    Message = "",
                )
            )
        }
    }
}