package com.example.apiuser.repository

import com.example.apiuser.data.remote.UserApi
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class UserRepository
@Inject
constructor(
    private val api: UserApi,
) {

}