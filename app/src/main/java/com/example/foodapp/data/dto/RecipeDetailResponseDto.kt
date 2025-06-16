package com.example.foodapp.data.dto

import com.example.foodapp.domain.model.RecipeDetailDomainModel
import java.util.Date

data class RecipeDetailResponseDto(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val extendedIngredients: List<IngredientDto>,
    val analyzedInstructions: List<InstructionDto>,
    val timestamp: Date?
)

data class IngredientDto(
    val original: String,
    val image: String
)

data class InstructionDto(
    val steps: List<StepDto>
)

data class StepDto(
    val number: Int,
    val step: String
)

fun RecipeDetailResponseDto.toDomain(
    isFavorite: Boolean,
    timestamp: Date? = null
): RecipeDetailDomainModel {
    return RecipeDetailDomainModel(
        id = this.id,
        title = this.title,
        image = this.image,
        summary = this.summary,
        extendedIngredients = this.extendedIngredients,
        analyzedInstructions = this.analyzedInstructions,
        isFavorite = isFavorite,
        timestamp = timestamp ?: this.timestamp ?: Date()
    )
}




