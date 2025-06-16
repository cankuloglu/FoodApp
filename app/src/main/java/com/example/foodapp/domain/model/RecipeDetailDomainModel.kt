package com.example.foodapp.domain.model

import com.example.foodapp.data.dto.IngredientDto
import com.example.foodapp.data.dto.InstructionDto
import java.util.Date

data class RecipeDetailDomainModel(
    override val id: Int,
    override val title: String,
    override val image: String,
    override val isFavorite: Boolean,
    override val summary: String,
    override val extendedIngredients: List<IngredientDto>,
    override val analyzedInstructions: List<InstructionDto>,
    override val timestamp: Date?
):IRecipeDetail
