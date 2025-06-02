package com.example.foodapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodapp.RecipeDao
import com.example.foodapp.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}