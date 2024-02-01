package com.example.apiuser.repository

import com.example.apiuser.data.remote.UserApi
import com.example.apiuser.data.remote.responses.Response
import com.example.apiuser.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class UserRepository
    @Inject
    constructor(
        private val api: UserApi,
    ) {
        suspend fun getUserList(results: Int): Resource<Response> {
            val response =
                try {
                    api.getUserList(results)
                } catch (e: Exception) {
                    return Resource.Error("Ошибка соединения")
                }
            return Resource.Success(response)
        }

        suspend fun getUserDetailList(
            seed: String,
            results: Int,
        ): Resource<Response> {
            val response =
                try {
                    api.getUserDetailList(seed, results)
                } catch (e: Exception) {
                    return Resource.Error("Ошибка соединения")
                }
            return Resource.Success(response)
        }
    }
