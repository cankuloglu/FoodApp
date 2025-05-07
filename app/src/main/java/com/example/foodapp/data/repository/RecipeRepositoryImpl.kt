package com.example.foodapp.data.repository

import com.example.foodapp.data.remote.SpoonacularApiService
import com.example.foodapp.data.dto.toDomain
import com.example.foodapp.domain.model.Recipe
import com.example.foodapp.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl@Inject constructor(
    private val api: SpoonacularApiService,
    private val apiKey: String
) : RecipeRepository {
    override suspend fun getRandomRecipes(): List<Recipe> {
        val response = api.getRandomRecipes(apiKey, 10)
        return response.recipes.map { it.toDomain() }
    }

    override suspend fun searchRecipes(query: String): List<Recipe> {
        return try {
            val response = api.searchRecipes(query, apiKey)
            response.results.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

}
