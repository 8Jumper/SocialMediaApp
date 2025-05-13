package com.example.socialmediaapp.ui.screens.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmediaapp.data.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class PostDetailViewModel : ViewModel() {

    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> = _post

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadPost(postId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val post = withContext(Dispatchers.IO) {
                fetchPost(postId)
            }
            _post.value = post
            _isLoading.value = false
        }
    }


    private fun fetchPost(postId: Int): Post? {
        return try {
            val json = URL("https://jsonplaceholder.typicode.com/posts/$postId").readText()
            val obj = JSONObject(json)
            Post(
                id = obj.getInt("id"),
                userId = obj.getInt("userId"),
                title = obj.getString("title"),
                body = obj.getString("body")
            )
        } catch (e: Exception) {
            null
        }
    }
}
