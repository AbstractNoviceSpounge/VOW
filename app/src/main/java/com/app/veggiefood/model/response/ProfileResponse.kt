package com.app.veggiefood.model.response

data class ProfileResponse(
    val status: Boolean,
    val data: ProfileModel
)

data class ProfileModel(
    val id: String,
    val fname: String,
    val lname: String,
    val email: String,
    val phone: String,
    val address: String,
    val city: String,
    val state: String,
    val pincode: String
)