package com.example.foodapp.domain.model

data class IngredientDomainModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val isSelected: Boolean = false
)
