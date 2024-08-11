package com.app.veggiefood.model.response

data class LoginResponse(
    val status: Boolean,
    val message: String,
    val user: LoginModel
)

data class LoginModel(
    val id: String,
    val fname: String,
    val lname: String,
    val email: String,
    val phone: String,
)
