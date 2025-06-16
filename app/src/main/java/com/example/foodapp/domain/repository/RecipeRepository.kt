package com.example.foodapp.domain.repository

import com.example.foodapp.domain.model.RecipeDomainModel
import com.example.foodapp.domain.model.RecipeDetailDomainModel
import kotlinx.coroutines.flow.Flow


interface RecipeRepository {
    suspend fun getRandomRecipes(): List<RecipeDomainModel>
    suspend fun searchRecipes(query: String): List<RecipeDomainModel>
    suspend fun getRecipeDetail(id:Int): RecipeDetailDomainModel
    suspend fun addFavoriteRecipe(recipe: RecipeDomainModel)
    fun getFavoriteRecipes(): Flow<List<RecipeDomainModel>>
    suspend fun deleteRecipes(recipe: RecipeDomainModel)
    fun sortByNameAsc(): Flow<List<RecipeDomainModel>>
    fun sortByNameDesc(): Flow<List<RecipeDomainModel>>
    fun sortByDateAsc(): Flow<List<RecipeDomainModel>>
    fun sortByDateDesc(): Flow<List<RecipeDomainModel>>
}
