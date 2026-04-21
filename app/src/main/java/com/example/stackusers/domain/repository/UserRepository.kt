package com.example.stackusers.domain.repository

import com.example.stackusers.domain.model.Result
import com.example.stackusers.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getTopUsers(): Result<List<User>>
    fun getFollowedUserIds(): Flow<Set<Long>>
    suspend fun followUser(userId: Long)
    suspend fun unfollowUser(userId: Long)
}
