package com.example.socialmediaapp.ui.screens.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmediaapp.data.model.Post
import com.example.socialmediaapp.data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import com.example.socialmediaapp.data.DataStoreManager

class PostDetailViewModel(private val repository: PostRepository, private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> = _post

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLiked = MutableStateFlow(false)
    val isLiked: StateFlow<Boolean> = _isLiked

    fun fetchPost(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _post.value = repository.getPost(id)
                _isLiked.value = dataStoreManager.isPostLiked(id)
            } catch (e: Exception) {
                _post.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleLike() {
        viewModelScope.launch {
            val id = _post.value?.id ?: return@launch
            dataStoreManager.togglePostLike(id)
            _isLiked.value = dataStoreManager.isPostLiked(id)
        }
    }
}
