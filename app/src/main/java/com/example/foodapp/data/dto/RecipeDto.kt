package com.example.foodapp.data.dto

import com.example.foodapp.domain.model.Recipe

data class RecipeDto(
    val id: Int,
    val title: String,
    val image: String
)

fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = this.id,
        title = this.title,
        image = this.image
    )
}
