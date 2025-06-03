package com.example.foodapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.domain.usecase.AddFavoriteRecipeUseCase
import com.example.foodapp.domain.model.Recipe
import com.example.foodapp.domain.usecase.GetRandomRecipesUseCase
import com.example.foodapp.domain.usecase.SearchRecipesUseCase
import com.example.foodapp.domain.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase,
    private val searchRecipesUseCase: SearchRecipesUseCase,
    private val addFavoriteRecipeUseCase: AddFavoriteRecipeUseCase,
) : ViewModel() {

    private val _recipesState = MutableStateFlow<ResponseState<List<Recipe>>>(ResponseState.Loading())
    val recipesState: StateFlow<ResponseState<List<Recipe>>> = _recipesState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    init {
        loadRandomRecipes()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        searchRecipes(query)
    }

    private fun loadRandomRecipes() {
        viewModelScope.launch {
            getRandomRecipesUseCase.execute()
                .collect { state ->
                    _recipesState.value = state
                }
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            searchRecipesUseCase.execute(query)
                .collect { state ->
                    _recipesState.value = state
                }
        }
    }

    fun addRecipeToFavorites(recipe: Recipe) {
        viewModelScope.launch {
            addFavoriteRecipeUseCase(recipe)
        }
    }
}
