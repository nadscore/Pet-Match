package com.example.petmatch.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petmatch.data.repository.PetRepositoryImpl
import com.example.petmatch.model.Pet
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Estados dos botões
enum class SwipeAction { NONE, LIKE, DISLIKE }

data class HomeUiState(
    val isLoading: Boolean = true,
    val pets: List<Pet> = emptyList(),
    val showMatchDialog: Boolean = false,
    val currentAction: SwipeAction = SwipeAction.NONE,
    val errorMessage: String? = null
)

class HomeViewModel : ViewModel() {

    private val repository = PetRepositoryImpl()
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadPets()
    }

    private fun loadPets() {
        viewModelScope.launch {
            val result = repository.getPets()
            result.onSuccess { petList ->
                _uiState.value = _uiState.value.copy(
                    pets = petList,
                    isLoading = false
                )
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun onDislike() {
        viewModelScope.launch {
            // 1. Fica vermelho
            _uiState.value = _uiState.value.copy(currentAction = SwipeAction.DISLIKE)
            delay(300)

            // 2. Remove o pet atual da lista
            removeCurrentPet()
        }
    }

    fun onLike() {
        // 1. Fica verde e mostra dialog
        _uiState.value = _uiState.value.copy(
            currentAction = SwipeAction.LIKE,
            showMatchDialog = true
        )
    }

    fun onDismissDialog() {
        _uiState.value = _uiState.value.copy(showMatchDialog = false)
        removeCurrentPet()
    }

    private fun removeCurrentPet() {
        val currentList = _uiState.value.pets.toMutableList()

        if (currentList.isNotEmpty()) {
            currentList.removeAt(0)
        }

        _uiState.value = _uiState.value.copy(
            pets = currentList,
            currentAction = SwipeAction.NONE
        )
    }
}