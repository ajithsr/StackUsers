package com.example.stackusers.data.repository

import com.example.stackusers.data.local.FollowLocalDataSource
import com.example.stackusers.data.remote.api.StackOverflowApi
import com.example.stackusers.data.remote.mapper.toDomain
import com.example.stackusers.domain.model.Result
import com.example.stackusers.domain.model.User
import com.example.stackusers.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val soApi: StackOverflowApi,
    private val localDataSource: FollowLocalDataSource
) : UserRepository {

    override suspend fun getTopUsers(): Result<List<User>> {
        return try {
            val response = soApi.getTopUsers()
            Result.Success(response.users.map { it.toDomain() })
        } catch (exc: Exception) {
            Result.Error(
                message = "Failed to load users. Please check your connection.",
                cause = exc
            )
        }
    }

    override fun getFollowedUserIds(): Flow<Set<Long>> = localDataSource.getFollowedUserIds()
    override suspend fun followUser(userId: Long) = localDataSource.followUser(userId)
    override suspend fun unfollowUser(userId: Long) = localDataSource.unfollowUser(userId)
}
