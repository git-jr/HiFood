package com.paradoxo.hifood.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginScreenStateViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginScreenState())
    val uiState = _uiState.asStateFlow()

    fun goToFormScreen() {
        _uiState.value = uiState.value.copy(gotToFormScreen = true)
    }

    fun goToLoginScreen() {
        _uiState.value = uiState.value.copy(gotToFormScreen = false)
    }

}