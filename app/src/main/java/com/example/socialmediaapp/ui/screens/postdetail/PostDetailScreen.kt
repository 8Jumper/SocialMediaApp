package com.example.socialmediaapp.ui.screens.postdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmediaapp.data.DataStoreManager
import com.example.socialmediaapp.data.network.RetrofitInstance
import com.example.socialmediaapp.data.repository.PostRepository
import com.example.socialmediaapp.ui.screens.home.HomeViewModel
import com.example.socialmediaapp.ui.screens.home.HomeViewModelFactory
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextField
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@SuppressLint("RememberReturnType")
@Composable
fun PostDetailScreen(navController: NavController, postId: Int) {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }

    val postViewModel: PostDetailViewModel = viewModel(
        factory = PostDetailViewModelFactory(PostRepository(RetrofitInstance.api), dataStoreManager)
    )
    val homeViewModelFactory: HomeViewModel = viewModel(factory = HomeViewModelFactory())
    val post by postViewModel.post.collectAsState()
    val isLoading by postViewModel.isLoading.collectAsState()

    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var imagePath by remember { mutableStateOf("") }

    val isLiked by postViewModel.isLiked.collectAsState()


    LaunchedEffect(Unit) {
        name = dataStoreManager.getName() ?: ""
        surname = dataStoreManager.getSurname() ?: ""
        imagePath = dataStoreManager.getImagePath() ?: ""
    }

    LaunchedEffect(postId) {
        postViewModel.fetchPost(postId)
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        post?.let {
            Column(
                Modifier
                    .padding(16.dp)
                    .systemBarsPadding()
            ) {
                Text(text = it.title, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Text(text = it.body)
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "User ID: ${it.userId}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(12.dp))

                Button(onClick = { postViewModel.toggleLike() }) {
                    Text(if (isLiked) "💔 Nie lubię" else "❤️ Lubię to")
                }

                Spacer(Modifier.height(24.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Wróć")
                }
            }
        } ?: Text("Nie znaleziono posta.")
    }
}