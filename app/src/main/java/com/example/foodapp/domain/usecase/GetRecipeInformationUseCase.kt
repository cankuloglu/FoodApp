package com.example.foodapp.domain.usecase

import com.example.foodapp.domain.model.RecipeDetailDomainModel
import com.example.foodapp.domain.repository.RecipeRepository
import com.example.foodapp.domain.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecipeInformationUseCase @Inject constructor(
    private val repository: RecipeRepository
): BaseUseCase<Int, ResponseState<RecipeDetailDomainModel>>(){

    override fun invokeStream(param: Int): Flow<ResponseState<RecipeDetailDomainModel>> = flow {
        emit(ResponseState.Loading())
        try {
            val recipeDetail = repository.getRecipeDetail(param)
            emit(ResponseState.Success(recipeDetail))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}
