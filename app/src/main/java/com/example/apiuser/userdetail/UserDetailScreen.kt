package com.example.apiuser.userdetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun UserDetailScreen(
    seed: String,
    userIndex: Int,
    navController: NavController,
    viewModel: UserDetailViewModel = hiltViewModel(),
) {
    Text(text = "seed: $seed \nuserIndex: $userIndex")
}
