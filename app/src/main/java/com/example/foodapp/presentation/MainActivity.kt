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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
                                if (currentRoute != "favorites") {
                                    Box(
                                        modifier = Modifier
                                            .padding(end = 8.dp)
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(
                                                MaterialTheme.colorScheme.onPrimary.copy(
                                                    alpha = 0.30f
                                                )
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
                            modifier = Modifier.padding(padding)
                        )
                    }
                )
            }
        }
    }
}




