package ui_elemente.model

data class ChatMessage(
    val id: String,
    val senderName: String,
    val text: String,
    val time: String,
    val isFromCurrentUser: Boolean
)