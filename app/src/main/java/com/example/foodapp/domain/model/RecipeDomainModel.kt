package com.example.foodapp.domain.model

data class RecipeDomainModel(
    override val id: Int,
    override val image: String,
    override val title: String
):IRecipe