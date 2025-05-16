package com.example.socialmediaapp.ui.screens.postdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    postId: Int,
    navController: NavController
) {
    val viewModel: PostDetailViewModel = remember { PostDetailViewModel() }
    val post by viewModel.post.collectAsState()

    LaunchedEffect(postId) {
        viewModel.loadPost(postId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Szczegóły posta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Wstecz")
                    }
                }
            )
        }
    ) { innerPadding ->
        post?.let {
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                Text(
                    text = "Tytuł:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = it.title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Treść:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = it.body,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "ID użytkownika: ${it.userId}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        } ?: Text("Ładowanie...", modifier = Modifier.padding(8.dp))
    }
}
