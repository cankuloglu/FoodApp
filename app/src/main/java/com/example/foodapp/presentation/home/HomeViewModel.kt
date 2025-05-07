package com.example.foodapp.presentation.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.domain.model.Recipe
import com.example.foodapp.domain.usecase.GetRandomRecipesUseCase
import com.example.foodapp.domain.usecase.SearchRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase,
    private val searchRecipesUseCase: SearchRecipesUseCase
) : ViewModel() {

    var searchRecipesList by mutableStateOf<List<Recipe>>(emptyList())
        private set

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

    fun searchRecipes(query: String){
        isLoading = true
        viewModelScope.launch {
            try {
                searchRecipesList = searchRecipesUseCase.execute(query)
                Log.d("HomeViewModel","Search results for '$query': $searchRecipesList")
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = "Search Failed"
                Log.e("HomeViewModel", "Search error", e)
            } finally {
                isLoading = false
            }

        }
    }
}