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

class PostDetailViewModel(private val repository: PostRepository) : ViewModel() {

    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> = _post

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchPost(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _post.value = repository.getPost(id)
            } catch (e: Exception) {
                _post.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}
