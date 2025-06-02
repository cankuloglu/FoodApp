package com.example.foodapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.domain.usecase.DeleteFavoriteRecipeUseCase
import com.example.foodapp.domain.usecase.GetFavoriteRecipesUseCase
import com.example.foodapp.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val deleteFavoriteRecipeUseCase: DeleteFavoriteRecipeUseCase,
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase
): ViewModel()
{
    private val _favoriteRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val favoriteRecipes: StateFlow<List<Recipe>> = _favoriteRecipes.asStateFlow()

    init {
        viewModelScope.launch {
            getFavoriteRecipesUseCase().collect {
                _favoriteRecipes.value = it
            }
        }
    }

    fun removeFavoriteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            deleteFavoriteRecipeUseCase(recipe)
        }
    }
}