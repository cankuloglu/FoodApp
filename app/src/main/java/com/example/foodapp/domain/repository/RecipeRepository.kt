package com.example.foodapp.domain.repository

import com.example.foodapp.domain.model.Recipe
import com.example.foodapp.domain.model.RecipeDetail


interface RecipeRepository {
    suspend fun getRandomRecipes(): List<Recipe>
    suspend fun searchRecipes(query: String): List<Recipe>
    suspend fun getRecipeDetail(id:Int): RecipeDetail
}
