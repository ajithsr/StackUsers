package com.example.stackusers.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("user_id") val userId: Long,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("reputation") val reputation: Int,
    @SerializedName("profile_image") val profileImage: String
)
