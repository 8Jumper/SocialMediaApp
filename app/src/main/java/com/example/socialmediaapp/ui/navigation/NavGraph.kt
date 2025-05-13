package com.example.socialmediaapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.socialmediaapp.ui.screens.home.HomeScreen
import com.example.socialmediaapp.ui.screens.postdetail.PostDetailScreen
import com.example.socialmediaapp.ui.screens.postdetail.PostDetailViewModel
import com.example.socialmediaapp.ui.screens.userdetail.UserDetailScreen

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onPostClick = { postId -> navController.navigate("post/$postId") },
                onUserClick = { userId -> navController.navigate("user/$userId") }
            )
        }

        composable("post/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toIntOrNull()
            if (postId != null) {
                PostDetailScreen(postId = postId, navController = navController)
            }
        }


        composable("user/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            UserDetailScreen(userId = userId, onBackClick = { navController.popBackStack() })
        }
    }
}

