package com.example.stackusers.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stackusers.R

@Composable
fun FollowButton(
    isFollowed: Boolean,
    onToggleFollow: () -> Unit
) {
    if (isFollowed) {
        OutlinedButton(
            onClick = onToggleFollow,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(
                text = stringResource(R.string.unfollow),
                style = MaterialTheme.typography.labelMedium
            )
        }
    } else {
        Button(
            onClick = onToggleFollow,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = stringResource(R.string.follow),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
fun FollowButtonPreview() {
    FollowButton(isFollowed = true, {})
}