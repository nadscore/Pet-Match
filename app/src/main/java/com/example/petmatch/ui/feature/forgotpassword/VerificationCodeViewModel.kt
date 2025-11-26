package com.example.petmatch.ui.feature.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class VerificationCodeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isCodeVerified: Boolean = false
)

class VerificationCodeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(VerificationCodeUiState())
    val uiState: StateFlow<VerificationCodeUiState> = _uiState.asStateFlow()

    fun validateCode(code: String) {
        if (code.isBlank()) {
            _uiState.value = VerificationCodeUiState(errorMessage = "Informe o código.")
            return
        }

        viewModelScope.launch {
            _uiState.value = VerificationCodeUiState(isLoading = true)

            try {
                delay(2000)

                _uiState.value = VerificationCodeUiState(
                    successMessage = "Código verificado com sucesso!",
                    isCodeVerified = true
                )
            } catch (e: Exception) {
                _uiState.value = VerificationCodeUiState(errorMessage = "Código inválido.")
            }
        }
    }

    fun resendCode() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(1500)
            _uiState.value = VerificationCodeUiState(successMessage = "Código reenviado para seu e-mail!")
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(errorMessage = null, successMessage = null)
    }
}