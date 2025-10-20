package com.example.raceme.profile.data

data class UserProfile(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val bio: String = "",
    val photoUri: String? = null
)
