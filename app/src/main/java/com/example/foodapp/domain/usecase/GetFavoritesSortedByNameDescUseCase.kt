package com.example.foodapp.domain.usecase

import com.example.foodapp.domain.model.RecipeDomainModel
import com.example.foodapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesSortedByNameDescUseCase @Inject constructor(
    private val repository: RecipeRepository
) : BaseUseCase<Unit, List<RecipeDomainModel>>() {

    override fun invokeStream(param: Unit): Flow<List<RecipeDomainModel>> {
        return repository.sortByNameDesc()
    }
}