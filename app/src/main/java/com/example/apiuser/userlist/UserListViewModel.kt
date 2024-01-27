package com.example.apiuser.userlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        val loadError = mutableStateOf("")

        init {
            loadUsers()
        }

        fun loadUsers() {
            viewModelScope.launch {
                val result = repository.getUserList(API_RESULTS)
                when (result) {
                    is Resource.Success -> {
                        val userEntries =
                            result.data?.results?.mapIndexed { index, entry ->
                                UserListEntry(
                                    name = entry.name,
                                    photo = entry.picture.medium,
                                    address = entry.location.street,
                                    phone = entry.phone,
                                )
                            }

                        loadError.value = ""
                        userList.value = userEntries!!
                    }
                    is Resource.Error -> {
                        loadError.value = result.message!!
                    }
                }
            }
        }
    }
