package com.example.foodapp.domain.repository

import com.example.foodapp.domain.model.Recipe
import com.example.foodapp.domain.model.RecipeDetail
import kotlinx.coroutines.flow.Flow


interface RecipeRepository {
    suspend fun getRandomRecipes(): List<Recipe>
    suspend fun searchRecipes(query: String): List<Recipe>
    suspend fun getRecipeDetail(id:Int): RecipeDetail
    suspend fun addFavoriteRecipe(recipe: Recipe)
    fun getFavoriteRecipes(): Flow<List<Recipe>>
    suspend fun deleteRecipes(recipe: Recipe)
}
