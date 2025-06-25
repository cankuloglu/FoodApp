package com.example.foodapp.data.dto

import com.example.foodapp.domain.model.IngredientDomainModel

data class IngredientAutoCompleteDto(
    val id: Int,
    val name: String,
    val image: String,
)

fun IngredientAutoCompleteDto.toDomain(): IngredientDomainModel {
    return IngredientDomainModel(
        id = this.id,
        name = this.name,
        imageUrl = "https://spoonacular.com/cdn/ingredients_100x100/${this.image}"
    )
}