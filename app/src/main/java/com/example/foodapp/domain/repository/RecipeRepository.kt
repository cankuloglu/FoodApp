package com.example.foodapp.domain.repository

import com.example.foodapp.domain.model.Recipe


interface RecipeRepository {
    suspend fun getRandomRecipes(): List<Recipe>
    suspend fun searchRecipes(query: String): List<Recipe>
}
