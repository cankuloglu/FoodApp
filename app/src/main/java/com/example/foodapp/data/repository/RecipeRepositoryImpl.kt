package com.example.foodapp.data.repository

import com.example.foodapp.RecipeDao
import com.example.foodapp.data.remote.SpoonacularApiService
import com.example.foodapp.data.dto.toDomain
import com.example.foodapp.domain.model.RecipeDomainModel
import com.example.foodapp.domain.model.RecipeDetailDomainModel
import com.example.foodapp.domain.repository.RecipeRepository
import com.example.foodapp.domain.util.Constants
import com.example.foodapp.toDomain
import com.example.foodapp.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl@Inject constructor(
    private val api: SpoonacularApiService,
    private val apiKey: String,
    private val recipeDao: RecipeDao
) : RecipeRepository {

    override suspend fun getRandomRecipes(): List<RecipeDomainModel> {
        val response = api.getRandomRecipes(apiKey, Constants.RANDOM_RECIPE_QUANTITY)
        return response.recipes.map { dto ->
            val isFavorite = recipeDao.isFavorite(dto.id)
            dto.toDomain(isFavorite)
        }
    }

    override suspend fun searchRecipes(query: String): List<RecipeDomainModel> {
        return try {
            val response = api.searchRecipes(query, apiKey)
            response.results.map { dto ->
                val isFavorite = recipeDao.isFavorite(dto.id)
                dto.toDomain(isFavorite)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getRecipeDetail(id: Int): RecipeDetailDomainModel {
        val responseDto = api.getRecipeInformation(id, apiKey, false)
        val isFavorite = recipeDao.isFavorite(id)
        return responseDto.toDomain(isFavorite)
    }

    override suspend fun addFavoriteRecipe(recipe: RecipeDomainModel) {
        recipeDao.insertRecipe(recipe.toEntity())
    }

    override fun getFavoriteRecipes(): Flow<List<RecipeDomainModel>> {
        return recipeDao.getAllFavoriteRecipes().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun deleteRecipes(recipe: RecipeDomainModel) {
        recipeDao.deleteRecipe(recipe.toEntity())
    }

    override fun sortByNameAsc(): Flow<List<RecipeDomainModel>> {
        return recipeDao.getFavoritesSortedByNameAsc()
            .map { list -> list.map { it.toDomain() } }
    }

    override fun sortByNameDesc(): Flow<List<RecipeDomainModel>> {
        return recipeDao.getFavoritesSortedByNameDesc()
            .map { list -> list.map { it.toDomain() }}
    }

    override fun sortByDateAsc(): Flow<List<RecipeDomainModel>> {
        return recipeDao.getFavoritesSortedByDateAsc()
            .map { list -> list.map { it.toDomain() } }
    }

    override fun sortByDateDesc(): Flow<List<RecipeDomainModel>> {
        return recipeDao.getFavoritesSortedByDateDesc()
            .map { list -> list.map { it.toDomain() } }
    }


}
