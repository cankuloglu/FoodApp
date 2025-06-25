package com.example.foodapp.presentation.ingredient

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.cankuloglu.myapplication.R
import com.example.foodapp.domain.util.ResponseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchByIngredientScreen(
    searchByIngredientScreenViewModel: SearchByIngredientScreenViewModel = hiltViewModel(),
    onRecipeClick: (Int) -> Unit
) {
    val ingredientState by searchByIngredientScreenViewModel.ingredientState.collectAsState()

    val searchQuery by searchByIngredientScreenViewModel.searchQuery.collectAsState()

    val selectedIngredients by searchByIngredientScreenViewModel.selectedIngredients.collectAsState()

    val recipeState by searchByIngredientScreenViewModel.recipeState.collectAsState()

    val isRecipeVisible by searchByIngredientScreenViewModel.isRecipeVisible.collectAsState()


    Scaffold(
        modifier = Modifier.padding(bottom = 80.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Food App",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0Xff008000),
                    titleContentColor = Color(0Xff008000)
                )
            )
        },
    ) { paddingValues ->

        Box {

            AnimatedVisibility(
                visible = isRecipeVisible,
                enter = fadeIn() + slideInHorizontally(initialOffsetX = { -200 }),
                exit = fadeOut() + slideOutHorizontally(targetOffsetX = { -200 }),
                modifier = Modifier
                    .zIndex(10f)
                    .align(Alignment.Center)
            ) {

                Box(
                    modifier = Modifier
                        .width(350.dp)
                        .heightIn(max = 500.dp)
                        .align(Alignment.Center)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        Spacer(modifier = Modifier.height(8.dp))

                        Card(
                            modifier = Modifier
                                .shadow(4.dp, shape = MaterialTheme.shapes.medium, clip = true),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp)
                            ) {
                                Text(
                                    text = "Recipes you can try",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    ),
                                    modifier = Modifier.align(Alignment.Center)
                                )

                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 12.dp)
                                        .size(24.dp)
                                        .clickable { searchByIngredientScreenViewModel.hideRecipes() }
                                )
                            }


                            when (val result = recipeState) {

                                is ResponseState.Loading -> {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }

                                is ResponseState.Success -> {
                                    LazyColumn(
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(max = 400.dp)
                                    ) {
                                        items(result.data) { recipe ->

                                            Card(
                                                modifier = Modifier
                                                    .padding(8.dp)
                                                    .fillMaxWidth()
                                                    .shadow(
                                                        4.dp,
                                                        shape = MaterialTheme.shapes.medium,
                                                        clip = true
                                                    )
                                                    .clickable { onRecipeClick(recipe.id) }
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(8.dp)

                                                ) {
                                                    AsyncImage(
                                                        model = recipe.image,
                                                        contentDescription = recipe.image,
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier.size(80.dp)
                                                    )

                                                    Spacer(modifier = Modifier.width(16.dp))

                                                    Text(
                                                        text = recipe.title,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                is ResponseState.Error -> {
                                    Text(
                                        text = "Error loading recipes: ${result.message}",
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding() + 16.dp,
                        bottom = 40.dp
                    )
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
            ) {

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { newText ->
                        searchByIngredientScreenViewModel.updateSearchQuery(newText)
                    },
                    placeholder = { Text("Search Ingredients") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    },
                    shape = MaterialTheme.shapes.medium,
                    textStyle = TextStyle(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (selectedIngredients.isNotEmpty()) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                    ) {
                        selectedIngredients.toList().forEach { ingredientName ->
                            Chip(
                                text = ingredientName,
                                onDelete = {
                                    searchByIngredientScreenViewModel.toggleIngredientSelection(
                                        ingredientName
                                    )
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = { searchByIngredientScreenViewModel.fetchRecipesBySelectedIngredients() },
                    colors = ButtonDefaults.buttonColors(Color(0xFFE92932)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .shadow(16.dp, shape = MaterialTheme.shapes.medium, clip = true)
                ) {
                    Text(
                        text = "Find Recipes",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                        .shadow(4.dp, shape = MaterialTheme.shapes.medium, clip = true),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {

                        if (searchQuery.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = if (searchQuery.isNotEmpty()) stringResource(R.string.search_results_text)
                            else "",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    when (val state = ingredientState) {
                        is ResponseState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(48.dp))
                            }
                        }

                        is ResponseState.Success -> {
                            if (selectedIngredients.isEmpty() && searchQuery.isEmpty()) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.ErrorOutline,
                                            contentDescription = null,
                                            tint = Color.Gray,
                                            modifier = Modifier.size(48.dp)
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(
                                            text = stringResource(R.string.no_ingredient_entered),
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                color = Color.Gray,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                            )
                                        )
                                    }
                                }
                            } else {
                                LazyColumn(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        items(items = state.data) { ingredient ->
                                            Card(
                                                modifier = Modifier
                                                    .padding(8.dp)
                                                    .fillMaxWidth()
                                                    .shadow(
                                                        4.dp,
                                                        shape = MaterialTheme.shapes.medium,
                                                        clip = true
                                                    )
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(8.dp)
                                                ) {
                                                    AsyncImage(
                                                        model = ingredient.imageUrl,
                                                        contentDescription = "Ingredient image",
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier.size(80.dp)
                                                    )

                                                    Spacer(modifier = Modifier.width(16.dp))

                                                    Text(
                                                        text = ingredient.name,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.weight(1f)
                                                    )

                                                    Log.d("ingscreen", "${ingredient.id}")

                                                    Spacer(modifier = Modifier.width(16.dp))

                                                    Icon(
                                                        imageVector = if (ingredient.isSelected) Icons.Default.Check else Icons.Default.Add,
                                                        contentDescription = null,
                                                        tint = Color.Black,
                                                        modifier = Modifier
                                                            .size(25.dp)
                                                            .clickable {
                                                                searchByIngredientScreenViewModel.toggleIngredientSelection(
                                                                    ingredient.name
                                                                )
                                                            }
                                                    )
                                                }
                                            }
                                        }
                                }

                            }

                        }

                        is ResponseState.Error -> {
                            Text(
                                text = "Error: ${state.message}",
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Chip(
    text: String,
    onDelete: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Color(0xFFF4F0F0),
        modifier = Modifier
            .height(32.dp)
            .padding(horizontal = 4.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = text,
                color = Color(0xFF181111),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete",
                tint = Color.Gray,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onDelete() }
            )
        }
    }
}





