package ui_elemente.model

data class ChatPreview (
    val chatId: String="",
    val otherUserId: String="",
    val otherUserEmail: String = "",
    val lastMessage: String = "",
    val lastMessageTime: String = ""
)