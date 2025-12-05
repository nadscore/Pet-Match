package com.example.petmatch.ui.feature.petregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petmatch.data.repository.PetRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PetRegisterUiState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

class PetRegisterViewModel : ViewModel() {
    private val repository = PetRepositoryImpl()
    private val _uiState = MutableStateFlow(PetRegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun savePet(nome: String, idade: String, descricao: String, imageUrl: String) {
        if (nome.isBlank() || idade.isBlank() || descricao.isBlank() || imageUrl.isBlank()) {
            _uiState.value = PetRegisterUiState(errorMessage = "Preencha tudo!")
            return
        }

        viewModelScope.launch {
            _uiState.value = PetRegisterUiState(isLoading = true)

            val idadeInt = idade.toIntOrNull() ?: 0

            val result = repository.savePet(nome, idadeInt, descricao, imageUrl)

            result.onSuccess {
                _uiState.value = PetRegisterUiState(successMessage = "Pet cadastrado com sucesso!")
            }.onFailure { e ->
                _uiState.value = PetRegisterUiState(errorMessage = "Erro: ${e.message}")
            }
        }
    }

    fun clearMessages() {
        _uiState.value = PetRegisterUiState()
    }
}