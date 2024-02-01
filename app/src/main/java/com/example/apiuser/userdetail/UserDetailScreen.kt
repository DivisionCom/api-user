package com.example.apiuser.userdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.util.LinkifyCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.apiuser.data.models.UserDetailEntry
import com.example.apiuser.ui.theme.RobotoCondensed
import java.util.Locale

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
                    .padding(16.dp, top = topPadding + userPhotoSize / 2f)
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
    val mContext = LocalContext.current

    Box(modifier = modifier) {
        Column {
            if (userDetailList.isNotEmpty()) {
                Text(
                    text =
                        "${userDetailList[userIndex].name.title} " +
                            "${userDetailList[userIndex].name.first} " +
                            userDetailList[userIndex].name.last,
                    textAlign = TextAlign.Center,
                    fontFamily = RobotoCondensed,
                    fontSize = 28.sp,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text =
                        "${userDetailList[userIndex].gender.capitalize(Locale.ROOT)}, " +
                            userDetailList[userIndex].nat,
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                val dob = userDetailList[userIndex].dob.date
                val birthday = dob.substring(0, Math.min(dob.length, 10))
                Text(
                    text =
                        "Birthday: " + birthday +
                            ", " +
                            "${userDetailList[userIndex].dob.age} y.o.",
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Contacts:",
                    textAlign = TextAlign.Center,
                    fontFamily = RobotoCondensed,
                    fontSize = 24.sp,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinkifyText(
                    text = "Phone: " + userDetailList[userIndex].phone,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .align(AbsoluteAlignment.Left),
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinkifyText(
                    text = userDetailList[userIndex].email,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .align(AbsoluteAlignment.Left),
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Location:",
                    textAlign = TextAlign.Center,
                    fontFamily = RobotoCondensed,
                    fontSize = 24.sp,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text =
                        userDetailList[userIndex].location.city +
                            ", " +
                            userDetailList[userIndex].location.state +
                            ", " +
                            userDetailList[userIndex].location.country,
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text =
                        userDetailList[userIndex].location.street.number.toString() +
                            ", " +
                            userDetailList[userIndex].location.street.name,
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text =
                        "(" +
                            userDetailList[userIndex].location.coordinates.latitude +
                            ", " +
                            userDetailList[userIndex].location.coordinates.longitude +
                            ")",
                    color = Color(0xFF02003d),
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                getLocationMap(
                                    latitude = userDetailList[userIndex].location.coordinates.latitude,
                                    longitude = userDetailList[userIndex].location.coordinates.longitude,
                                    mContext = mContext,
                                )
                            },
                )
            }
        }
    }
}

private fun getLocationMap(
    latitude: String,
    longitude: String,
    mContext: Context,
) {
    val gmmIntentUri = Uri.parse("geo:$latitude,$longitude")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    mContext.startActivity(mapIntent)
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

@Composable
fun LinkifyText(
    text: String,
    modifier: Modifier,
) {
    val mContext = LocalContext.current
    val mCustomLinkifyText =
        remember {
            TextView(mContext)
        }
    AndroidView(
        factory = { mCustomLinkifyText },
        modifier =
        modifier,
    ) { textView ->
        textView.text = text
        textView.textSize = 20F
        textView.setLinkTextColor(0xFF02003d.toInt())
        textView.setTextColor(0xFF000000.toInt())
        LinkifyCompat.addLinks(textView, Linkify.ALL)
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}
