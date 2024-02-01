package com.example.apiuser.userdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.apiuser.data.models.UserDetailEntry
import com.example.apiuser.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
    @Inject
    constructor(
        private val repository: UserRepository,
    ) : ViewModel() {
        val userDetailList = mutableStateOf<List<UserDetailEntry>>(listOf())
    }
