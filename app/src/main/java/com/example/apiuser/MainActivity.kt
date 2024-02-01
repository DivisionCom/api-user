package com.example.apiuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apiuser.ui.theme.APIUserTheme
import com.example.apiuser.userdetail.UserDetailScreen
import com.example.apiuser.userlist.UserListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APIUserTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "user_list_screen",
                ) {
                    composable("user_list_screen") {
                        UserListScreen(navController = navController)
                    }
                    composable(
                        "user_detail_screen/{seed}/{userIndex}",
                        arguments =
                            listOf(
                                navArgument("seed") {
                                    type = NavType.StringType
                                },
                                navArgument("userIndex") {
                                    type = NavType.StringType
                                },
                            ),
                    ) {
                        val seed =
                            remember {
                                it.arguments?.getString("seed")
                            }
                        val userIndex =
                            remember {
                                it.arguments?.getString("userIndex")
                            }
                        userIndex?.toInt()?.let { it1 ->
                            seed?.let { it2 ->
                                UserDetailScreen(
                                    seed = it2,
                                    userIndex = it1,
                                    navController = navController,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
