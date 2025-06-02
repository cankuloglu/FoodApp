package com.example.foodapp.domain.usecase

import com.example.foodapp.domain.model.Recipe
import com.example.foodapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    operator fun invoke(): Flow<List<Recipe>> {
        return repository.getFavoriteRecipes()
    }
}