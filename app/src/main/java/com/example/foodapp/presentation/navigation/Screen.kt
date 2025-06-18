package com.example.foodapp.presentation.navigation

sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "Home")
    data object Favorites : Screen("favorites", "Favorites")
}