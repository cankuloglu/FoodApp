package com.example.foodapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.domain.usecase.GetRandomRecipesUseCase
import com.example.foodapp.domain.usecase.SearchRecipesUseCase
import com.example.foodapp.presentation.uistate.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase,
    private val searchRecipesUseCase: SearchRecipesUseCase
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    private var currentQuery = ""

    init {
        loadRandomRecipes()
    }

    private fun loadRandomRecipes() {
        currentQuery = ""
        viewModelScope.launch {
            _homeUiState.value = HomeUiState.Loading
            try {
                val recipes = getRandomRecipesUseCase.execute()
                _homeUiState.value = if (recipes.isNotEmpty())
                    HomeUiState.Success(recipes)
                else
                    HomeUiState.Empty
            } catch (e: Exception) {
                _homeUiState.value = HomeUiState.Error("Failed to load random recipes")
            }
        }
    }

    fun searchRecipes(query: String) {
        currentQuery = query
        viewModelScope.launch {
            _homeUiState.value = HomeUiState.Loading
            try {
                val recipes = searchRecipesUseCase.execute(query)
                _homeUiState.value = if (recipes.isNotEmpty())
                    HomeUiState.Success(recipes)
                else
                    HomeUiState.Empty
            } catch (e: Exception) {
                _homeUiState.value = HomeUiState.Error("Search Failed")
            }
        }
    }
}
