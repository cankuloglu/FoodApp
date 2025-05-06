package com.example.foodapp

import com.example.foodapp.data.SpoonacularApiService
import javax.inject.Inject

class RecipeRepository@Inject constructor(
    private val api: SpoonacularApiService,
    private val apiKey: String
) {
    suspend fun getRandomRecipes(): List<Recipe> {
        val response = api.getRandomRecipes(apiKey, 10)
        return response.recipes
    }

}
