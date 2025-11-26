package com.example.petmatch.ui.feature.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ForgotPasswordUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class ForgotPasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    fun sendResetCode(email: String) {
        if (email.isBlank()) {
            _uiState.value = ForgotPasswordUiState(errorMessage = "Por favor, informe o e-mail.")
            return
        }

        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState(isLoading = true)

            try {
                delay(2000)

                _uiState.value = ForgotPasswordUiState(
                    successMessage = "Código enviado para $email!"
                )
            } catch (e: Exception) {
                _uiState.value = ForgotPasswordUiState(errorMessage = "Erro ao enviar: ${e.message}")
            }
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(errorMessage = null, successMessage = null)
    }
}