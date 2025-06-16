package com.example.foodapp.domain.model

import java.util.Date

data class RecipeDomainModel(
    override val id: Int,
    override val image: String,
    override val title: String,
    override val isFavorite: Boolean,
    override val timestamp: Date?
):IRecipe