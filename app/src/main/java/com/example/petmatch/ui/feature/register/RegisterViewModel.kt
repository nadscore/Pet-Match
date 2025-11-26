package com.example.petmatch.ui.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petmatch.data.repository.UserRepositoryImpl
import com.example.petmatch.util.CpfUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registerSuccess: Boolean = false
)

class RegisterViewModel : ViewModel() {

    private val repository = UserRepositoryImpl()

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(nome: String, cpf: String, email: String, senha: String) {

        // 1. Limpa o CPF (remove tudo que não é número)
        val cpfLimpo = cpf.filter { it.isDigit() }

        // 2. Validações Básicas (Campos vazios)
        if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
            _uiState.value = RegisterUiState(errorMessage = "Preencha todos os campos!")
            return
        }

        // 3. Validação Matemática do CPF
        if (!CpfUtil.myValidateCPF(cpfLimpo)) {
            _uiState.value = RegisterUiState(errorMessage = "CPF Inválido! Verifique os números.")
            return
        }

        viewModelScope.launch {
            _uiState.value = RegisterUiState(isLoading = true)

            try {
                // TODO: Futuramente chamaremos repository.register(..., cpfLimpo, ...)

                delay(2000) // Simulando o tempo de rede

                _uiState.value = RegisterUiState(registerSuccess = true)

            } catch (e: Exception) {
                _uiState.value = RegisterUiState(errorMessage = "Erro ao registrar: ${e.message}")
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}