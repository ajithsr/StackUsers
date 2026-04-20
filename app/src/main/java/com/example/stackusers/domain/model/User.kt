package com.example.stackusers.domain.model

data class User(
    val userId: Long,
    val displayName: String,
    val reputation: Int,
    val profileImageUrl: String
)
