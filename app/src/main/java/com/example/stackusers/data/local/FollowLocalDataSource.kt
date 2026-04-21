package com.example.stackusers.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FollowLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val TAG = "FollowLocalDataSource"
        private val KEY_FOLLOWED_USER_IDS = stringSetPreferencesKey("followed_user_ids")
    }

    fun getFollowedUserIds(): Flow<Set<Long>> =
        dataStore.data.map { preferences ->
            preferences[KEY_FOLLOWED_USER_IDS]
                ?.mapNotNull { it.toLongOrNull() }
                ?.toSet()
                ?: emptySet()
        }

    suspend fun followUser(userId: Long) {
        Log.d(TAG, "followUser -> $userId")
        dataStore.edit { preferences ->
            val current = preferences[KEY_FOLLOWED_USER_IDS] ?: emptySet()
            preferences[KEY_FOLLOWED_USER_IDS] = current + userId.toString()
        }
    }

    suspend fun unfollowUser(userId: Long) {
        Log.d(TAG, "unfollowUser -> $userId")
        dataStore.edit { preferences ->
            val current = preferences[KEY_FOLLOWED_USER_IDS] ?: emptySet()
            preferences[KEY_FOLLOWED_USER_IDS] = current - userId.toString()
        }
    }
}
