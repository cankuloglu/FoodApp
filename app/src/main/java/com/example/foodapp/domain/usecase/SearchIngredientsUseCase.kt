package com.example.foodapp.domain.usecase

import com.example.foodapp.domain.model.IngredientDomainModel
import com.example.foodapp.domain.repository.RecipeRepository
import com.example.foodapp.domain.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchIngredientsUseCase@Inject constructor(
    private val repository: RecipeRepository
) : StreamBaseUseCase<String, ResponseState<List<IngredientDomainModel>>> {

    override fun invokeStream(param: String): Flow<ResponseState<List<IngredientDomainModel>>> = flow {
        emit(ResponseState.Loading())
        try {
            val ingredients = repository.searchIngredient(param)
            emit(ResponseState.Success(ingredients))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}