package com.example.socialmediaapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.socialmediaapp.ui.screens.home.HomeScreen
import com.example.socialmediaapp.ui.screens.postdetail.PostDetailScreen
import com.example.socialmediaapp.ui.screens.profile.ProfileScreen
import com.example.socialmediaapp.ui.screens.userdetail.UserDetailScreen

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }

        composable("postDetail/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toIntOrNull()
            if (postId != null) {
                PostDetailScreen(navController, postId = postId)
            }
        }


        composable("userDetail/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            UserDetailScreen(navController, userId = userId)
        }

        composable("myprofile") {
            ProfileScreen(navController)
        }
    }
}

