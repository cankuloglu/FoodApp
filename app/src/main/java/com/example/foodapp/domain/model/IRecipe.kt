package com.example.foodapp.domain.model

import java.util.Date

interface IRecipe {
    val id: Int
    val title: String
    val image: String
    val isFavorite: Boolean
    val timestamp: Date?
}