package com.example.foodapp

import com.example.foodapp.domain.model.Recipe

fun RecipeEntity.toDomain() = Recipe(id, title, image)
fun Recipe.toEntity() = RecipeEntity(id, title, image)
