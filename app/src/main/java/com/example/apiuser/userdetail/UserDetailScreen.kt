package com.example.apiuser.userdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.apiuser.data.models.UserDetailEntry
import timber.log.Timber

@Composable
fun UserDetailScreen(
    seed: String,
    userIndex: Int,
    navController: NavController,
    viewModel: UserDetailViewModel = hiltViewModel(),
) {
    val userDetailList by remember {
        viewModel.userDetailList
    }

    initViewModel(
        userDetailList = userDetailList,
        seed = seed,
        userIndex = userIndex,
        viewModel = viewModel,
    )
}

fun initViewModel(
    userDetailList: List<UserDetailEntry>,
    seed: String,
    userIndex: Int,
    viewModel: UserDetailViewModel,
) {
    if (seed.isNotEmpty()) {
        if (userDetailList.isEmpty()) {
            viewModel.loadUserDetailList(seed)
        } else {
            Timber.d("UserDebugDetail: ${userDetailList[userIndex].name}")
        }
    }
}
