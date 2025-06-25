package com.example.foodapp.presentation.ingredient

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.domain.model.IngredientDomainModel
import com.example.foodapp.domain.model.RecipeDomainModel
import com.example.foodapp.domain.usecase.GetRecipeByIngredientUseCase
import com.example.foodapp.domain.usecase.SearchIngredientsUseCase
import com.example.foodapp.domain.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchByIngredientScreenViewModel@Inject constructor(
    private val searchIngredientsUseCase: SearchIngredientsUseCase,
    private val getRecipeByIngredientUseCase: GetRecipeByIngredientUseCase
): ViewModel() {

    private val _ingredientState = MutableStateFlow<ResponseState<List<IngredientDomainModel>>>(
        ResponseState.Success(emptyList())
    )
    val ingredientState: StateFlow<ResponseState<List<IngredientDomainModel>>> = _ingredientState.asStateFlow()

    private val _selectedIngredients = MutableStateFlow<List<String>>(emptyList())
    val selectedIngredients: StateFlow<List<String>> = _selectedIngredients.asStateFlow()

    private val _recipeState = MutableStateFlow<ResponseState<List<RecipeDomainModel>>>(ResponseState.Success(emptyList()))
    val recipeState: StateFlow<ResponseState<List<RecipeDomainModel>>> = _recipeState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isRecipeVisible = MutableStateFlow(false)
    val isRecipeVisible: StateFlow<Boolean> = _isRecipeVisible.asStateFlow()


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isNotBlank()) {
            searchIngredients(query)
        } else {
            _ingredientState.value = ResponseState.Success(emptyList())
        }
    }

    private fun searchIngredients(query: String) {
        viewModelScope.launch {
            searchIngredientsUseCase.invokeStream(query)
                .collect { state ->
                    if (state is ResponseState.Success) {
                        val selectedNames = selectedIngredients.value
                        val updatedList = state.data.map { ingredient ->
                            ingredient.copy(isSelected = selectedNames.contains(ingredient.name))
                        }
                        _ingredientState.value = ResponseState.Success(updatedList)
                    } else {
                        _ingredientState.value = state
                    }
                }

        }
    }

    fun toggleIngredientSelection(ingredientName: String) {
        val current = selectedIngredients.value.toMutableSet()
        if (current.contains(ingredientName)) {
            current.remove(ingredientName)
        } else {
            current.add(ingredientName)
        }

        _selectedIngredients.value = current.toList()

        val currentState = _ingredientState.value
        if (currentState is ResponseState.Success) {
            val updatedList = currentState.data.map { ingredient ->
                ingredient.copy(isSelected = current.contains(ingredient.name))
            }
            _ingredientState.value = ResponseState.Success(updatedList)
        }
    }

    fun fetchRecipesBySelectedIngredients() {
        val ingredientsList = _selectedIngredients.value

        viewModelScope.launch {
            getRecipeByIngredientUseCase.invokeStream(ingredientsList)
                .onStart {
                    _recipeState.value = ResponseState.Loading()
                }
                .catch { e ->
                    Log.e("RecipeFetch", "Error fetching recipes: ${e.message}")
                    _recipeState.value = ResponseState.Error(e.message ?: "Unknown error")
                }
                .collect { recipes ->
                    Log.d("RecipeFetch", "Received recipes: ${recipes.joinToString { it.id.toString() }}")
                    _recipeState.value = ResponseState.Success(recipes)
                    showRecipes()
                }
        }
    }

    fun showRecipes() {
        _isRecipeVisible.value = true
    }

    fun hideRecipes() {
        _isRecipeVisible.value = false
    }



}