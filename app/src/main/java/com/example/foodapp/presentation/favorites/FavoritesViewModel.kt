package com.example.foodapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.domain.usecase.GetFavoriteRecipesUseCase
import com.example.foodapp.domain.model.RecipeDomainModel
import com.example.foodapp.domain.usecase.ToggleFavoriteRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase,
    private val toggleFavoriteRecipeUseCase: ToggleFavoriteRecipeUseCase
): ViewModel()
{
    private val _favoriteRecipes = MutableStateFlow<List<RecipeDomainModel>>(emptyList())
    val favoriteRecipes: StateFlow<List<RecipeDomainModel>> = _favoriteRecipes.asStateFlow()


    init {
        viewModelScope.launch {
            getFavoriteRecipesUseCase.invokeStream(Unit).collect {
                _favoriteRecipes.value = it
            }
        }
    }

    fun removeFavoriteRecipe(recipe: RecipeDomainModel) {
        viewModelScope.launch {
            toggleFavoriteRecipeUseCase(recipe)

        }
    }

}