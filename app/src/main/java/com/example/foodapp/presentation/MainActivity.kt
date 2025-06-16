package com.example.foodapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodapp.presentation.favorites.FavoritesViewModel
import com.example.foodapp.presentation.navigation.NavGraph
import com.example.foodapp.ui.theme.FoodAppTheme
import com.example.foodapp.ui.theme.greenPrimary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodAppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val favoritesViewModel: FavoritesViewModel = hiltViewModel()

                var menuExpanded by remember { mutableStateOf(false) }

                Scaffold(
                    containerColor = Color.White,
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Food App",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            },
                            navigationIcon = {
                                if (currentRoute != "home") {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back",
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                            },
                            actions = {
                                when (currentRoute) {
                                    "home" -> {
                                        Box(
                                            modifier = Modifier
                                                .padding(end = 8.dp)
                                                .size(40.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.30f)
                                                )
                                                .clickable { navController.navigate("favorites") },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Favorite,
                                                contentDescription = "Go to Favorites",
                                                tint = Color.Red
                                            )
                                        }
                                    }

                                    "favorites" -> {

                                        Box {
                                            IconButton(onClick = { menuExpanded = true }) {
                                                Icon(
                                                    imageVector = Icons.Default.MoreVert,
                                                    contentDescription = "More options",
                                                    tint = MaterialTheme.colorScheme.onPrimary
                                                )
                                            }
                                            DropdownMenu(
                                                expanded = menuExpanded,
                                                onDismissRequest = { menuExpanded = false }
                                            ) {
                                                DropdownMenuItem(
                                                    text = { Text("Sort by ascending name") },
                                                    onClick = {
                                                        menuExpanded = false
                                                        favoritesViewModel.setSortType(FavoritesViewModel.SortType.NAME_ASC)
                                                    }
                                                )
                                                DropdownMenuItem(
                                                    text = { Text("Sort by descending name ") },
                                                    onClick = {
                                                        menuExpanded = false
                                                        favoritesViewModel.setSortType(FavoritesViewModel.SortType.NAME_DESC)
                                                    }
                                                )
                                                DropdownMenuItem(
                                                    text = { Text("Sort by ascending date ") },
                                                    onClick = {
                                                        menuExpanded = false
                                                        favoritesViewModel.setSortType(FavoritesViewModel.SortType.DATE_ASC)
                                                    }
                                                )
                                                DropdownMenuItem(
                                                    text = { Text("Sort by descending date ") },
                                                    onClick = {
                                                        menuExpanded = false
                                                        favoritesViewModel.setSortType(FavoritesViewModel.SortType.DATE_DESC)
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = greenPrimary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    },
                    content = { padding ->
                        NavGraph(
                            navController = navController,
                            modifier = Modifier.padding(padding),
                            favoritesViewModel = favoritesViewModel
                        )
                    }
                )
            }
        }
    }
}






