package com.example.stackusers.data.remote.repository

import com.example.stackusers.data.remote.api.StackOverflowApi
import com.example.stackusers.domain.repository.UserRepository
import com.example.stackusers.data.remote.mapper.toDomain
import com.example.stackusers.domain.model.User
import com.example.stackusers.domain.model.Result
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val soApi: StackOverflowApi
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
}
