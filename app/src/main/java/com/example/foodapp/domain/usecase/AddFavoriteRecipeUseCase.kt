package com.example.foodapp.domain.usecase

import com.example.foodapp.domain.model.RecipeDomainModel
import com.example.foodapp.domain.repository.RecipeRepository
import javax.inject.Inject

class AddFavoriteRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) : BaseUseCase<RecipeDomainModel, Unit>() {

    override suspend fun invoke(param: RecipeDomainModel) {
        repository.addFavoriteRecipe(param)
    }
}