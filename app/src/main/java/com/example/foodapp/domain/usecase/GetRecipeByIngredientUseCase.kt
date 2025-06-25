package com.example.foodapp.domain.usecase

import com.example.foodapp.domain.model.RecipeDomainModel
import com.example.foodapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecipeByIngredientUseCase @Inject constructor(
    private val repository: RecipeRepository
) : BaseUseCase<List<String>, List<RecipeDomainModel>>() {

    override fun invokeStream(param: List<String>): Flow<List<RecipeDomainModel>> = flow {
        val result = repository.getRecipesByIngredients(param)
        emit(result)
    }
}