package com.example.foodapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.domain.model.RecipeDetail
import com.example.foodapp.domain.usecase.GetRecipeInformationUseCase
import com.example.foodapp.domain.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
     private val getRecipeInformationUseCase: GetRecipeInformationUseCase
): ViewModel() {

    private val _recipeDetailState = MutableStateFlow<ResponseState<RecipeDetail>>(ResponseState.Loading())
    val recipeDetailState: StateFlow<ResponseState<RecipeDetail>> = _recipeDetailState.asStateFlow()

    fun loadRecipeDetail(id: Int) {
        viewModelScope.launch {
            getRecipeInformationUseCase.execute(id)
                .collect { state ->
                    _recipeDetailState.value = state
                }
        }
    }
}