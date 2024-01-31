package com.example.apiuser.userlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiuser.data.models.UserDetailEntry
import com.example.apiuser.data.models.UserListEntry
import com.example.apiuser.repository.UserRepository
import com.example.apiuser.util.Constants.API_RESULTS
import com.example.apiuser.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel
    @Inject
    constructor(
        private val repository: UserRepository,
    ) : ViewModel() {
        val userList = mutableStateOf<List<UserListEntry>>(listOf())
        val userDetail = mutableStateOf<List<UserDetailEntry>>(listOf())
        val loadError = mutableStateOf("")
        val isRefreshing = mutableStateOf(false)

        init {
            loadUsers()
        }

        fun loadUsers() {
            viewModelScope.launch {
                val result = repository.getUserList(API_RESULTS)
                when (result) {
                    is Resource.Success -> {
                        isRefreshing.value = true
                        val userEntries =
                            result.data?.results?.mapIndexed { index, entry ->
                                UserListEntry(
                                    name = entry.name,
                                    photo = entry.picture,
                                    street = entry.location.street,
                                    phone = entry.phone,
                                    city = entry.location.city,
                                )
                            }
                        val userDetailEntries =
                            result.data?.results?.mapIndexed{ index, entry ->
                                UserDetailEntry(
                                    gender = entry.gender,
                                    name = entry.name,
                                    location = entry.location,
                                    email = entry.email,
                                    login = entry.login,
                                    dob = entry.dob,
                                    registered = entry.registered,
                                    phone = entry.phone,
                                    cell = entry.cell,
                                    id = entry.id,
                                    picture = entry.picture,
                                    nat = entry.nat,
                                )
                            }

                        loadError.value = ""
                        userList.value = userEntries!!
                        userDetail.value = userDetailEntries!!
                        isRefreshing.value = false
                    }
                    is Resource.Error -> {
                        loadError.value = result.message!!
                    }
                }
            }
        }
    }
