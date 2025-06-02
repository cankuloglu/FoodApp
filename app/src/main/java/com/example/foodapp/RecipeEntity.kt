package com.example.foodapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val image: String
)
