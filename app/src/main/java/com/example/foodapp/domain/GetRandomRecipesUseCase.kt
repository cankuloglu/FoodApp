package com.example.foodapp.domain

import com.example.foodapp.Recipe
import com.example.foodapp.RecipeRepository
import javax.inject.Inject

class GetRandomRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend fun execute(): List<Recipe>{
        return repository.getRandomRecipes()
    }
}