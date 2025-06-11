package com.example.foodapp.domain.usecase

import com.example.foodapp.domain.model.RecipeDomainModel
import com.example.foodapp.domain.repository.RecipeRepository
import javax.inject.Inject

class DeleteFavoriteRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) : BaseUseCase<RecipeDomainModel, Unit>() {

    override suspend fun invoke(param: RecipeDomainModel) {
        repository.deleteRecipes(param)
    }
}

