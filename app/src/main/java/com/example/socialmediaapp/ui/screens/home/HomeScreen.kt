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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())
    val posts by viewModel.posts.collectAsState()
    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedUserId by remember { mutableStateOf<Int?>(null) }

    if (isLoading) {
        CircularProgressIndicator()
    } else if (error != null) {
        Text("Błąd: $error")
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("myprofile") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Mój profil")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Filtruj po użytkowniku:")
            Box {
                Button(onClick = { expanded = true }) {
                    Text(text = selectedUserId?.let { id ->
                        users.find { it.id == id }?.name ?: "Wszyscy"
                    } ?: "Wszyscy")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Wszyscy") },
                        onClick = {
                            selectedUserId = null
                            expanded = false
                        }
                    )
                    users.forEach { user ->
                        DropdownMenuItem(
                            text = { Text(user.name) },
                            onClick = {
                                selectedUserId = user.id
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                val filteredPosts = selectedUserId?.let { id ->
                    posts.filter { it.userId == id }
                } ?: posts

                items(filteredPosts.size) { index ->
                    val post = filteredPosts[index]
                    val user = users.find { it.id == post.userId }

                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate("postDetail/${post.id}")
                            }
                    ) {
                        Text(text = post.title, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "by ${user?.name ?: "Unknown"}",
                            modifier = Modifier.clickable {
                                navController.navigate("userDetail/${post.userId}")
                            }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}
