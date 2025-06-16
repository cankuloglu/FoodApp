package com.example.foodapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao

interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM favorite_recipes")
    fun getAllFavoriteRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM favorite_recipes ORDER BY title  ASC")
    fun getFavoritesSortedByNameAsc(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM favorite_recipes ORDER BY title DESC")
    fun getFavoritesSortedByNameDesc(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM favorite_recipes ORDER BY timestamp ASC")
    fun getFavoritesSortedByDateAsc(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM favorite_recipes ORDER BY timestamp DESC")
    fun getFavoritesSortedByDateDesc(): Flow<List<RecipeEntity>>

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_recipes WHERE id = :recipeId)")
    suspend fun isFavorite(recipeId: Int): Boolean



}
