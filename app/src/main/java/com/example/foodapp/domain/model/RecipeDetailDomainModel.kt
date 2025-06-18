package com.example.foodapp.domain.model

import com.example.foodapp.data.dto.IngredientDto
import com.example.foodapp.data.dto.InstructionDto
import java.util.Date

data class RecipeDetailDomainModel(
    val id: Int,
    val title: String,
    val image: String,
    val isFavorite: Boolean,
    val summary: String,
    val extendedIngredients: List<IngredientDto>,
    val analyzedInstructions: List<InstructionDto>,
    val timestamp: Date?
):IRecipeDetail
