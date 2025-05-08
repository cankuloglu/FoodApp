package com.example.foodapp.domain.model

import com.example.foodapp.data.dto.IngredientDto
import com.example.foodapp.data.dto.InstructionDto

data class RecipeDetail(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val extendedIngredients: List<IngredientDto>,
    val analyzedInstructions: List<InstructionDto>
)
