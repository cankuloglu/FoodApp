package com.example.foodapp.domain.model

import com.example.foodapp.data.dto.IngredientDto
import com.example.foodapp.data.dto.InstructionDto

interface IRecipeDetail : IRecipe {
    val summary: String
    val extendedIngredients: List<IngredientDto>
    val analyzedInstructions: List<InstructionDto>
}