package com.example.foodapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.foodapp.presentation.detail.DetailScreen
import com.example.foodapp.presentation.favorites.FavoritesScreen
import com.example.foodapp.presentation.home.HomeScreen
import com.example.foodapp.presentation.ingredient.SearchByIngredientScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier,
){
    NavHost(
        navController = navController,
        startDestination = "home") {

        composable("home") {
            HomeScreen(
                modifier = modifier,
                onRecipeClick = { recipeId ->
                    navController.navigate("detail/$recipeId")
                }
            )
        }

        composable("detail/{recipeId}") { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull()
            recipeId?.let {
                DetailScreen(
                    recipeId = it,
                    modifier = modifier,
                    onNavigateFavoritesClicked = {
                        navController.navigate("favorites")
                    },
                    navController = navController
                )
            }
        }

        composable("favorites"){
            FavoritesScreen(
                modifier = modifier,
                onRecipeClick = { recipeId ->
                    navController.navigate("detail/$recipeId")
                },
                navController = navController
            )
        }

        composable("search_by_ingredients"){
            SearchByIngredientScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate("detail/$recipeId")
                }
            )
        }
    }
}