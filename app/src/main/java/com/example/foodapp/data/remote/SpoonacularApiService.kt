package com.example.foodapp.data.remote

import com.example.foodapp.data.dto.RandomRecipeResponseDto
import com.example.foodapp.data.dto.RecipeDetailResponseDto
import com.example.foodapp.data.dto.SearchRecipeResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApiService {

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ): RandomRecipeResponseDto

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String
    ): SearchRecipeResponseDto

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String,
        @Query("includeNutrition") includeNutrition: Boolean = false
    ): RecipeDetailResponseDto
}