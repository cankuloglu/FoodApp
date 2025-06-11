package com.example.foodapp.domain.usecase

import com.example.foodapp.domain.model.RecipeDomainModel
import javax.inject.Inject

class ToggleFavoriteRecipeUseCase @Inject constructor(
    private val addFavoriteRecipeUseCase: AddFavoriteRecipeUseCase,
    private val deleteFavoriteRecipeUseCase: DeleteFavoriteRecipeUseCase
) {
    suspend operator fun invoke(recipe: RecipeDomainModel): Boolean {
        return if (recipe.isFavorite) {
            deleteFavoriteRecipeUseCase(recipe)
            false
        } else {
            addFavoriteRecipeUseCase(recipe)
            true
        }
    }
}
