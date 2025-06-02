package com.example.foodapp.domain.usecase

import com.example.foodapp.domain.model.Recipe
import com.example.foodapp.domain.repository.RecipeRepository
import javax.inject.Inject

class AddFavoriteRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(recipe: Recipe) {
        repository.addFavoriteRecipe(recipe)
    }
}