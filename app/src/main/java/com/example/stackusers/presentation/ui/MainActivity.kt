package com.example.stackusers.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stackusers.domain.model.User
import com.example.stackusers.presentation.feature.userslist.UiState
import com.example.stackusers.presentation.feature.userslist.UsersListViewModel
import com.example.stackusers.presentation.theme.StackUsersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val usersListViewModel: UsersListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StackUsersTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val uiState by usersListViewModel.uiState.collectAsState()
                    Box(modifier = Modifier.padding(innerPadding))
                    when(val state = uiState) {
                        is UiState.Loading -> {
                            CircularProgressIndicator()
                        }
                        is UiState.Success -> {
                            val users = state.data as List<User>
                            UsersList(users, Modifier.padding(innerPadding))
                        }
                        is UiState.Error -> {}
                    }
                }
            }
        }
    }
}


@Composable
fun UsersList(users: List<User>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    )
    {
        items(
            items = users,
            key = { it.userId }
        ) { user ->
            Text(
                modifier = Modifier.padding(5.dp),
                text = user.displayName
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