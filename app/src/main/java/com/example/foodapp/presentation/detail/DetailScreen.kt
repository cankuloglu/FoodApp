package com.example.foodapp.presentation.detail

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen(
    recipeId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(recipeId) {
        viewModel.loadRecipeDetail(recipeId)
    }

    val recipeDetail = viewModel.recipeDetail

    Log.d("DetailScreen","$recipeDetail")
}