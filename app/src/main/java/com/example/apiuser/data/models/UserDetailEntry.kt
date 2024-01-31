package com.example.apiuser.data.models

import com.example.apiuser.data.remote.responses.Dob
import com.example.apiuser.data.remote.responses.Id
import com.example.apiuser.data.remote.responses.Location
import com.example.apiuser.data.remote.responses.Login
import com.example.apiuser.data.remote.responses.Name
import com.example.apiuser.data.remote.responses.Picture
import com.example.apiuser.data.remote.responses.Registered

data class UserDetailEntry(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: Dob,
    val registered: Registered,
    val phone: String,
    val cell: String,
    val id: Id,
    val picture: Picture,
    val nat: String,
)
