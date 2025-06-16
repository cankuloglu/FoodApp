package com.example.foodapp.data.dto

import com.example.foodapp.domain.model.RecipeDomainModel
import java.util.Date

data class RecipeDto(
    val id: Int,
    val title: String,
    val image: String,
    val timestamp: Date
)

fun RecipeDto.toDomain(isFavorite: Boolean): RecipeDomainModel {
    return RecipeDomainModel(
        id = this.id,
        title = this.title,
        image = this.image,
        isFavorite = isFavorite,
        timestamp = this.timestamp
    )
}
