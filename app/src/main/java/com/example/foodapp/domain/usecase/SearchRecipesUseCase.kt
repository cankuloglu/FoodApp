package com.example.foodapp.domain.usecase

import com.example.foodapp.domain.model.RecipeDomainModel
import com.example.foodapp.domain.repository.RecipeRepository
import com.example.foodapp.domain.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
): BaseUseCase<String, ResponseState<List<RecipeDomainModel>>>(){

    override fun invokeStream(param: String): Flow<ResponseState<List<RecipeDomainModel>>> = flow {
        emit(ResponseState.Loading())

        try {
            val recipes = repository.searchRecipes(param)
            emit(ResponseState.Success(recipes))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}