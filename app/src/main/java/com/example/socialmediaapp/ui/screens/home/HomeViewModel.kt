package com.example.socialmediaapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmediaapp.data.model.PostWithUser
import com.example.socialmediaapp.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(val posts: List<PostWithUser>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel : ViewModel() {
    private val repository = PostRepository()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                val posts = repository.getPosts()
                val users = repository.getUsers()
                val userMap = users.associateBy { it.id }

                val postWithUsers = posts.map { post ->
                    PostWithUser(post, userMap[post.userId])
                }

                _uiState.value = HomeUiState.Success(postWithUsers)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Błąd: ${e.localizedMessage}")
            }
        }
    }
}
