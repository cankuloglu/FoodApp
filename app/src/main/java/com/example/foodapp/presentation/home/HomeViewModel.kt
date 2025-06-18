package com.example.foodapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.domain.model.RecipeDomainModel
import com.example.foodapp.domain.usecase.GetFavoriteRecipesUseCase
import com.example.foodapp.domain.usecase.GetRandomRecipesUseCase
import com.example.foodapp.domain.usecase.SearchRecipesUseCase
import com.example.foodapp.domain.usecase.ToggleFavoriteRecipeUseCase
import com.example.foodapp.domain.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase,
    private val searchRecipesUseCase: SearchRecipesUseCase,
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase,
    private val toggleFavoriteRecipeUseCase: ToggleFavoriteRecipeUseCase
) : ViewModel() {

    private val _recipesState = MutableStateFlow<ResponseState<List<RecipeDomainModel>>>(ResponseState.Loading())
    val recipesState: StateFlow<ResponseState<List<RecipeDomainModel>>> = _recipesState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _favoriteRecipes = MutableStateFlow<List<RecipeDomainModel>>(emptyList())

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadRandomRecipes()
        viewModelScope.launch {
            getFavoriteRecipesUseCase.invokeStream(Unit).collect { favoriteList ->
                _favoriteRecipes.value = favoriteList
                updateRecipesWithFavorites(favoriteList)
            }
        }
    }

    private fun updateRecipesWithFavorites(favorites: List<RecipeDomainModel>) {
        val currentState = _recipesState.value
        if (currentState is ResponseState.Success) {
            val updatedList = currentState.data.map { recipe ->
                recipe.copy(isFavorite = favorites.any { it.id == recipe.id })
            }
            _recipesState.value = ResponseState.Success(updatedList)
        }
    }


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        searchRecipes(query)
    }

    private fun loadRandomRecipes() {
        viewModelScope.launch {
            getRandomRecipesUseCase.invokeStream(Unit).collect { state ->
                    _recipesState.value = state
                }
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            searchRecipesUseCase.invokeStream(query)
                .collect { state ->
                    _recipesState.value = state
                }
        }
    }

    fun toggleFavoriteState(recipe: RecipeDomainModel) {
        viewModelScope.launch {
            toggleFavoriteRecipeUseCase(recipe)

            if (recipe.isFavorite) {
                _uiEvent.send(UiEvent.ShowSnackbar(
                    message = "${recipe.title} is removed from favorites",
                    actionLabel = "Undo"
                ))
            } else {
                _uiEvent.send(UiEvent.ShowSnackbar(
                    message = "${recipe.title} is added to favorites",
                    actionLabel = "Undo"
                ))
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(
            val message: String,
            val actionLabel: String? = null
        ) : UiEvent()
    }

}
