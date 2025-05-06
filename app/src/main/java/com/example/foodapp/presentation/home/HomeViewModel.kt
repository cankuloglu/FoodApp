package com.example.foodapp.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.Recipe
import com.example.foodapp.domain.GetRandomRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase
) : ViewModel() {

    var randomRecipesList by mutableStateOf<List<Recipe>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadRandomRecipes()
    }

    private fun loadRandomRecipes() {
        isLoading = true
        viewModelScope.launch {
            try {
                randomRecipesList = getRandomRecipesUseCase.execute()
                Log.d("HomeViewModel", "Random recipes loaded: $randomRecipesList")
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = "Failed to load random recipes"
                Log.e("HomeViewModel", "Error loading random recipes", e)
            } finally {
                isLoading = false
            }
        }


    }
}