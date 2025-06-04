package com.example.foodapp.domain.model

import com.example.foodapp.data.dto.IngredientDto
import com.example.foodapp.data.dto.InstructionDto

data class RecipeDetailDomainModel(
    override val id: Int,
    override val title: String,
    override val image: String,
    override val summary: String,
    override val extendedIngredients: List<IngredientDto>,
    override val analyzedInstructions: List<InstructionDto>
):IRecipeDetail
