package com.example.petmatch.ui.feature.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class NewPasswordUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isResetComplete: Boolean = false
)

class NewPasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewPasswordUiState())
    val uiState: StateFlow<NewPasswordUiState> = _uiState.asStateFlow()

    fun resetPassword(senha: String, confirmarSenha: String) {
        // 1. Validação de campos vazios
        if (senha.isBlank() || confirmarSenha.isBlank()) {
            _uiState.value = NewPasswordUiState(errorMessage = "Preencha as duas senhas.")
            return
        }

        // 2. Validação se as senhas batem
        if (senha != confirmarSenha) {
            _uiState.value = NewPasswordUiState(errorMessage = "As senhas não coincidem.")
            return
        }

        viewModelScope.launch {
            _uiState.value = NewPasswordUiState(isLoading = true)

            try {
                delay(2000)

                _uiState.value = NewPasswordUiState(
                    successMessage = "Senha redefinida com sucesso!",
                    isResetComplete = true
                )
            } catch (e: Exception) {
                _uiState.value = NewPasswordUiState(errorMessage = "Erro ao redefinir senha.")
            }
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(errorMessage = null, successMessage = null)
    }
}