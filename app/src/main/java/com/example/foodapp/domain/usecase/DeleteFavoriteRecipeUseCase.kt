package com.example.foodapp.domain.usecase

import com.example.foodapp.domain.model.Recipe
import com.example.foodapp.domain.repository.RecipeRepository
import javax.inject.Inject

class DeleteFavoriteRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(recipe: Recipe){
        repository.deleteRecipes(recipe)
    }
}
