package com.example.foodapp.presentation.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.domain.model.RecipeDetail
import com.example.foodapp.domain.usecase.GetRecipeInformationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val getRecipeInformationUseCase: GetRecipeInformationUseCase
): ViewModel() {

    var recipeDetail by mutableStateOf<RecipeDetail?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadRecipeDetail(id: Int) {
        isLoading = true
        viewModelScope.launch {
            try {
                recipeDetail = getRecipeInformationUseCase.execute(id)
                Log.d("DetailViewModel","Recipe Detail: $recipeDetail")
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = "Failed to load recipe detail"
            } finally {
                isLoading = false
            }
        }
    }
}