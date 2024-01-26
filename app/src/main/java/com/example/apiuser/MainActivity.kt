package com.example.apiuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apiuser.ui.theme.APIUserTheme
import com.example.apiuser.userlist.UserListScreen

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
                    composable("user_detail_screen") {
                    }
                }
            }
        }
    }
}
