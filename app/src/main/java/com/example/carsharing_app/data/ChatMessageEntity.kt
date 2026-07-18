package com.example.carsharing_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey


data class ChatMessageEntity(
    val id: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val text: String = "",
    val time: String = "",
    val timestamp: Long = 0,
    val isFromCurrentUser: Boolean = false
)