package com.example.apiuser.userlist

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.apiuser.data.models.UserListEntry
import timber.log.Timber

@Composable
fun UserListScreen(
    navController: NavController,
    viewModel: UserListViewModel = hiltViewModel(),
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
    ) {
        UserList(navController = navController)
    }
}

@Composable
fun UserList(
    navController: NavController,
    viewModel: UserListViewModel = hiltViewModel(),
) {
    val userList by remember {
        viewModel.userList
    }
    val loadError by remember {
        viewModel.loadError
    }
    Timber.d("UserDebug: ${userList.size}")
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount =
            if (userList.size % 2 == 0) {
                userList.size
            } else {
                userList.size + 1
            }
        items(itemCount) {
            UserRow(rowIndex = it, entries = userList, navController = navController)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadUsers()
            }
        }
    }
}

@Composable
fun UserRow(
    rowIndex: Int,
    entries: List<UserListEntry>,
    navController: NavController,
) {
    Row {
        UserEntry(
            entry = entries[rowIndex],
            navController = navController,
            modifier = Modifier.weight(1f),
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun UserEntry(
    entry: UserListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = hiltViewModel(),
) {
    Box(
        modifier =
            modifier
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .border(2.dp, Color.White, RoundedCornerShape(10.dp))
                .padding(8.dp)
                .aspectRatio(1.25f)
                .clickable {
                    // onClick Event
                },
    ) {
        Column {
            SubcomposeAsyncImage(
                model =
                    ImageRequest.Builder(LocalContext.current)
                        .data(entry.photo.large)
                        .crossfade(true)
                        .build(),
                contentDescription = entry.name.last,
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.scale(0.5F),
                    )
                },
                modifier =
                    Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally)
                        .shadow(5.dp, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color.White, RoundedCornerShape(10.dp)),
            )
            Text(
                text = "${entry.name.title} ${entry.name.first} ${entry.name.last}",
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .fillMaxWidth(),
            )
            Text(
                text = "${entry.street.number} ${entry.street.name}, ${entry.city}",
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .fillMaxWidth(),
            )
            Text(
                text = "Phone: ${entry.phone}",
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .fillMaxWidth(),
            )
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit,
) {
    Column {
        Text(
            text = error,
            color = Color.Red,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier =
                Modifier
                    .align(CenterHorizontally),
        ) {
            Text(text = "Повторить попытку")
        }
    }
}
