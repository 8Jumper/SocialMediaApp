package com.example.socialmediaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.socialmediaapp.ui.screens.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onPostClick = { postId -> navController.navigate("post/$postId") },
                onUserClick = { userId -> navController.navigate("user/$userId") }
            )
        }
        // Dodasz później:
        // composable("post/{postId}") { ... }
        // composable("user/{userId}") { ... }
    }
}
