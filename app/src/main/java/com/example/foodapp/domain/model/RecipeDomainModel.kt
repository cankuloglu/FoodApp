package com.example.foodapp.domain.model

import java.util.Date

data class RecipeDomainModel(
    val id: Int,
    val image: String,
    val title: String,
    val isFavorite: Boolean,
    val timestamp: Date?
):IRecipe