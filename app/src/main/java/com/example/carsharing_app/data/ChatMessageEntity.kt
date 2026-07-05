package com.example.carsharing_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val senderName: String,
    val text: String,
    val time: String,
    val isFromCurrentUser: Boolean
)