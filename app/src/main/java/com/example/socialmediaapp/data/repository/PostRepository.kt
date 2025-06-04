package com.example.socialmediaapp.data.repository

import com.example.socialmediaapp.data.model.Post
import com.example.socialmediaapp.data.model.Todo
import com.example.socialmediaapp.data.model.User
import com.example.socialmediaapp.data.network.ApiService


class PostRepository(private val api: ApiService) {

    suspend fun getPosts(): List<Post> = api.getPosts()
    suspend fun getUsers(): List<User> = api.getUsers()
    suspend fun getPost(id: Int): Post = api.getPost(id)
    suspend fun getUser(id: Int): User = api.getUser(id)
    suspend fun getTodos(userId: Int): List<Todo> = api.getTodos(userId)
}
