package com.example.socialmediaapp.ui.screens.userdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmediaapp.data.network.RetrofitInstance
import com.example.socialmediaapp.data.repository.PostRepository
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun UserDetailScreen(navController: NavController, userId: Int) {
    val context = LocalContext.current
    val viewModel: UserDetailViewModel = viewModel(
        factory = UserDetailViewModelFactory(PostRepository(RetrofitInstance.api))
    )
    val user by viewModel.user.collectAsState()
    val todos by viewModel.todos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(userId) {
        viewModel.fetchUserData(userId)
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        user?.let {
            Column(
                Modifier
                    .padding(16.dp)
                    .systemBarsPadding()
            ) {
                Text(text = it.name, style = MaterialTheme.typography.titleLarge)
                Text(text = "@${it.username}")
                Text(text = "📧 ${it.email}")
                Text(text = "📞 ${it.phone}")
                Text(text = "🌐 ${it.website}")
                Text(text = "🏢 ${it.company.name}")
                Text(text = "📍 ${it.address.street}, ${it.address.city}")

                Spacer(Modifier.height(16.dp))

                val lat = it.address.geo.lat.toDoubleOrNull() ?: 0.0
                val lng = it.address.geo.lng.toDoubleOrNull() ?: 0.0
                val userLocation = LatLng(lat, lng)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(userLocation, 12f)
                }

                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(position = userLocation),
                        title = it.name
                    )
                }

                Spacer(Modifier.height(16.dp))

                Button(onClick = { navController.popBackStack() }) {
                    Text("Wróć")
                }

                Spacer(Modifier.height(16.dp))

                Text("Zadania:", style = MaterialTheme.typography.titleMedium)
                LazyColumn {
                    items(todos.size) { index ->
                        val todo = todos[index]
                        Text(text = "${if (todo.completed) "✅" else "❌"} ${todo.title}")
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        } ?: Text("Nie znaleziono użytkownika.")
    }
}