package com.example.foodapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.domain.model.RecipeDetailDomainModel
import com.example.foodapp.domain.model.RecipeDomainModel
import com.example.foodapp.domain.usecase.GetFavoriteRecipesUseCase
import com.example.foodapp.domain.usecase.GetRecipeInformationUseCase
import com.example.foodapp.domain.usecase.ToggleFavoriteRecipeUseCase
import com.example.foodapp.domain.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
     private val getRecipeInformationUseCase: GetRecipeInformationUseCase,
     private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase,
     private  val toggleFavoriteRecipeUseCase: ToggleFavoriteRecipeUseCase
): ViewModel() {

    private val _recipeDetailDomainModelState = MutableStateFlow<ResponseState<RecipeDetailDomainModel>>(ResponseState.Loading())
    val recipeDetailDomainModelState: StateFlow<ResponseState<RecipeDetailDomainModel>> = _recipeDetailDomainModelState.asStateFlow()

    private val _favoriteRecipes = MutableStateFlow<List<RecipeDomainModel>>(emptyList())

    init {
        viewModelScope.launch {
            getFavoriteRecipesUseCase.invokeStream(Unit).collect {
                _favoriteRecipes.value = it
            }
        }
    }

    fun loadRecipeDetail(id: Int) {
        viewModelScope.launch {
            getRecipeInformationUseCase.invokeStream(id).collect { state ->
                    _recipeDetailDomainModelState.value = state
                }
        }
    }

    fun toggleFavoriteState(recipe: RecipeDomainModel) {
        viewModelScope.launch {
            toggleFavoriteRecipeUseCase(recipe)

            _recipeDetailDomainModelState.update { currentState ->
                if (currentState is ResponseState.Success) {
                    val currentRecipe = currentState.data
                    val updatedRecipe = currentRecipe.copy(isFavorite = !currentRecipe.isFavorite)
                    ResponseState.Success(updatedRecipe)
                } else {
                    currentState
                }
            }
        }
    }

}