package com.example.stackusers.domain.repository

import com.example.stackusers.domain.model.Result
import com.example.stackusers.domain.model.User

interface UserRepository {
    suspend fun getTopUsers(): Result<List<User>>

}
