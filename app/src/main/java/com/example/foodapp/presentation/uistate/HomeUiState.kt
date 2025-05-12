package com.example.foodapp.presentation.uistate
import com.example.foodapp.domain.model.Recipe

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data object Empty : HomeUiState()
    data class Success(val recipes: List<Recipe>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
