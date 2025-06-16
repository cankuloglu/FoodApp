package com.example.foodapp

import com.example.foodapp.domain.model.RecipeDetailDomainModel
import com.example.foodapp.domain.model.RecipeDomainModel
import java.util.Date

fun RecipeEntity.toDomain(): RecipeDomainModel {
    return RecipeDomainModel(
        id = this.id,
        title = this.title,
        image = if (image.startsWith("http")) image
        else "https://spoonacular.com/recipeImages/$image",
        isFavorite = true,
        timestamp = this.timestamp ?: Date(0)
    )
}

fun RecipeDomainModel.toEntity(): RecipeEntity {
    return RecipeEntity(
        id = id,
        title = title,
        image = image,
        timestamp = Date()
    )
}

fun RecipeDetailDomainModel.toRecipeDomainModel(): RecipeDomainModel {
    return RecipeDomainModel(
        id = this.id,
        title = this.title,
        image = this.image,
        isFavorite = this.isFavorite,
        timestamp = timestamp

    )
}

