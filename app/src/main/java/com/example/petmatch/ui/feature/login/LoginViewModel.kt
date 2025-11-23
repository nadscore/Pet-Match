package com.example.petmatch.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petmatch.data.repository.UserRepositoryImpl
import com.example.petmatch.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val usuario: User? = null,
    val loginSuccess: Boolean = false
)

class LoginViewModel : ViewModel() {
    private val repository = UserRepositoryImpl()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    fun login(email: String, senha: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)

            val result = repository.login(email, senha)

            result.onSuccess { usuario ->
                _uiState.value = LoginUiState(
                    usuario = usuario,
                    loginSuccess = true
                )
            }.onFailure { exception ->
                _uiState.value = LoginUiState(
                    errorMessage = exception.message ?: "Erro desconhecido"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}