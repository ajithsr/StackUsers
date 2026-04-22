package com.example.stackusers.presentation.feature.userslist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stackusers.R
import com.example.stackusers.domain.model.User
import com.example.stackusers.presentation.components.ErrorContent
import com.example.stackusers.presentation.components.LoadingContent
import com.example.stackusers.presentation.components.UserItem
import com.example.stackusers.presentation.theme.StackUsersTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(
    viewModel: UsersListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.top_users_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { _ ->

        when(val state = uiState) {
            is UiState.Loading -> {
                LoadingContent()
            }
            is UiState.Success -> {
                val users = state.data as List<User>
                UsersList(
                    users,
                    onToggleFollow =
                        { userId,
                          isFollowed -> viewModel.toggleFollow(userId, isFollowed)
                        }
                )
            }
            is UiState.Error -> {
                ErrorContent(state.message, {
                    viewModel.loadUsers()
                })
            }
        }
    }
}

@Composable
fun UsersList(
    users: List<User>,
    onToggleFollow: (userId: Long, isFollowed: Boolean) -> Unit = { _, _ -> }
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(
            items = users, key = { it.userId }
        ) { user ->
            UserItem(
                user,
                onToggleFollow = { onToggleFollow(user.userId, user.isFollowed) },
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsersListPreview() {
    val users: List<User> = listOf(
        User(1, "Alpha", 1, ""),
        User(2, "Beta", 2, "")
    )
    StackUsersTheme {
        UsersList(users)
    }
}

