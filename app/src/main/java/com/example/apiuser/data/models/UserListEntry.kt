package com.example.apiuser.data.models

import com.example.apiuser.data.remote.responses.Name
import com.example.apiuser.data.remote.responses.Picture
import com.example.apiuser.data.remote.responses.Street

data class UserListEntry(
    val name: Name,
    val photo: Picture,
    val street: Street,
    val phone: String,
    val city: String,
)
