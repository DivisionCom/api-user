package com.example.apiuser.userdetail

import androidx.lifecycle.ViewModel
import com.example.apiuser.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
    @Inject
    constructor(
        private val repository: UserRepository,
    ) : ViewModel() {

    }
