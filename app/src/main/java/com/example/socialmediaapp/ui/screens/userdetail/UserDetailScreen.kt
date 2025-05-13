package com.example.socialmediaapp.ui.screens.userdetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun UserDetailScreen(userId: String?) {
    Text(text = "User ID: $userId")
}
