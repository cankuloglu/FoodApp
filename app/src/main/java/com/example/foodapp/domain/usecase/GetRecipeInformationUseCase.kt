package com.example.foodapp.domain.usecase

import com.example.foodapp.domain.model.Recipe
import com.example.foodapp.domain.model.RecipeDetail
import com.example.foodapp.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeInformationUseCase@Inject constructor(
    private val repository: RecipeRepository
) {
    suspend fun execute(id:Int): RecipeDetail {
        return repository.getRecipeDetail(id)
    }
}