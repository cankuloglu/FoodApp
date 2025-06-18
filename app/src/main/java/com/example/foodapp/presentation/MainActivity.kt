package com.example.foodapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.foodapp.presentation.components.BottomNavigationBar
import com.example.foodapp.presentation.navigation.NavGraph
import com.example.foodapp.ui.theme.FoodAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodAppTheme {
                val navController = rememberNavController()

                Scaffold(
                    containerColor = Color.White,
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    },
                    content = { padding ->
                        NavGraph(
                            navController = navController,
                            modifier = Modifier.padding(padding),
                        )
                    }
                )
            }
        }
    }
}






