package com.example.socialmediaapp.data.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    val companyName: String,
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String
)
