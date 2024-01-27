package com.example.apiuser.data.models

import com.example.apiuser.data.remote.responses.Name
import com.example.apiuser.data.remote.responses.Street

data class UserListEntry(
    val name: Name,
    val photo: String,
    val address: Street,
    val phone: String,
)
