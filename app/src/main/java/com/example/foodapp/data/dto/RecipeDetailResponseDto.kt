package com.example.foodapp.data.dto

import com.example.foodapp.domain.model.RecipeDetailDomainModel

data class RecipeDetailResponseDto(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val extendedIngredients: List<IngredientDto>,
    val analyzedInstructions: List<InstructionDto>
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

fun RecipeDetailResponseDto.toDomain(): RecipeDetailDomainModel {
    return RecipeDetailDomainModel(
        id = this.id,
        title = this.title,
        image = this.image,
        summary = this.summary,
        extendedIngredients = this.extendedIngredients,
        analyzedInstructions = this.analyzedInstructions
    )
}



