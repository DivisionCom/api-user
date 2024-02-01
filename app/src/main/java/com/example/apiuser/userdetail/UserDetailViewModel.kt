package com.example.apiuser.userdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiuser.data.models.UserDetailEntry
import com.example.apiuser.repository.UserRepository
import com.example.apiuser.util.Constants.API_RESULTS
import com.example.apiuser.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
    @Inject
    constructor(
        private val repository: UserRepository,
    ) : ViewModel() {
        var userDetailList = mutableStateOf<List<UserDetailEntry>>(listOf())
        val loadError = mutableStateOf("")

        fun loadUserDetailList(seed: String) {
            viewModelScope.launch {
                val result = repository.getUserDetailList(seed, API_RESULTS)
                when (result) {
                    is Resource.Success -> {
                        val userDetailEntries =
                            result.data?.results?.mapIndexed { index, entry ->
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
                        userDetailList.value = userDetailEntries!!
                    }
                    is Resource.Error -> {
                        loadError.value = result.message!!
                    }
                }
            }
        }
    }
