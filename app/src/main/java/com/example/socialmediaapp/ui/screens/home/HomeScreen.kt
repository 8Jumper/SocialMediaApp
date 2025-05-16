package com.example.socialmediaapp.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    onPostClick: (Int) -> Unit,
    onUserClick: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is HomeUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is HomeUiState.Error -> {
            val error = (state as HomeUiState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.fetchData() }) {
                        Text("Spróbuj ponownie")
                    }
                }
            }
        }
        is HomeUiState.Success -> {
            val posts = (state as HomeUiState.Success).posts
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(posts) { postWithUser ->
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPostClick(postWithUser.post.id) }
                        .padding(8.dp)) {

                        Text(text = postWithUser.post.title, style = MaterialTheme.typography.titleMedium)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = postWithUser.user?.name ?: "Nieznany autor",
                            modifier = Modifier.clickable {
                                postWithUser.user?.let { user -> onUserClick(user.id) }
                            }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}
