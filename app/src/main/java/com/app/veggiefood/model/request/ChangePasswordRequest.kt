package com.app.veggiefood.model.request

data class ChangePasswordRequest(
    val userId:String,
    val oldPassword:String,
    val newPassword:String
)
