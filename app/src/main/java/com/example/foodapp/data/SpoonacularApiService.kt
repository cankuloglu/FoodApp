package com.example.foodapp.data

import com.example.foodapp.RandomRecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpoonacularApiService {

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ): RandomRecipeResponse
}