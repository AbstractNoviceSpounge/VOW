package com.app.veggiefood.model.response

data class NotificationResponse(

    val status:String,
    val data:ArrayList<NotificationModel>

)
data class NotificationModel(
    val id:String,
    val user_id:String,
    val message:String,
    val created_at:String
)
