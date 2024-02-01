package com.example.apiuser.userdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.apiuser.data.models.UserDetailEntry

@Composable
fun UserDetailScreen(
    seed: String,
    userIndex: Int,
    navController: NavController,
    viewModel: UserDetailViewModel = hiltViewModel(),
    userPhotoSize: Dp = 200.dp,
    topPadding: Dp = 20.dp,
) {
    val userDetailList by remember {
        viewModel.userDetailList
    }

    initViewModel(
        userDetailList = userDetailList,
        seed = seed,
        viewModel = viewModel,
    )

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
    ) {
        UserDetailTopSection(
            navController = navController,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .align(Alignment.TopCenter),
        )
        UserDetailWrapper(
            userIndex = userIndex,
            userDetailList = userDetailList,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        top = topPadding + userPhotoSize / 2f,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    )
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            if (userDetailList.isNotEmpty()) {
                userDetailList[userIndex].picture.large.let {
                    SubcomposeAsyncImage(
                        model =
                            ImageRequest.Builder(LocalContext.current)
                                .data(it)
                                .crossfade(true)
                                .build(),
                        contentDescription = userDetailList[userIndex].name.last,
                        loading = {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.scale(0.5F),
                            )
                        },
                        modifier =
                            Modifier
                                .size(userPhotoSize)
                                .offset(y = topPadding)
                                .shadow(5.dp, RoundedCornerShape(100.dp))
                                .clip(RoundedCornerShape(100.dp))
                                .border(2.dp, Color.White, RoundedCornerShape(100.dp)),
                    )
                }
            }
        }
    }
}

@Composable
fun UserDetailWrapper(
    userIndex: Int,
    userDetailList: List<UserDetailEntry>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
    }
}

@Composable
fun UserDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier =
            modifier
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black,
                            Color.Transparent,
                        ),
                    ),
                ),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier =
                Modifier
                    .size(36.dp)
                    .offset(16.dp, 16.dp)
                    .clickable {
                        navController.popBackStack()
                    },
        )
    }
}

fun initViewModel(
    userDetailList: List<UserDetailEntry>,
    seed: String,
    viewModel: UserDetailViewModel,
) {
    if (seed.isNotEmpty()) {
        if (userDetailList.isEmpty()) {
            viewModel.loadUserDetailList(seed)
        }
    }
}
