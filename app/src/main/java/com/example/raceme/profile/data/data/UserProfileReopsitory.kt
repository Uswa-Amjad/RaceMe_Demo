package com.example.raceme.profile.data

interface UserProfileRepository {
    suspend fun getUserProfile(userId: String): UserProfile
    suspend fun saveUserProfile(profile: UserProfile)
}
