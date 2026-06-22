package ui_elemente.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui_elemente.model.ChatMessage
import kotlin.time.Duration.Companion.milliseconds

class ChatViewModel : ViewModel() {

    var messages by mutableStateOf<List<ChatMessage>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadMessages() {
        viewModelScope.launch {
            isLoading = true

            // simuliert Laden aus Internet / Datenbank
            delay(1000.milliseconds)

            messages = listOf(
                ChatMessage(
                    id = "1",
                    senderName = "John Doe",
                    text = "Hi! Are you still available for the ride?",
                    time = "10:30",
                    isFromCurrentUser = false
                ),
                ChatMessage(
                    id = "2",
                    senderName = "Me",
                    text = "Yes, I am!",
                    time = "10:32",
                    isFromCurrentUser = true
                ),
                ChatMessage(
                    id = "3",
                    senderName = "John Doe",
                    text = "Great! See you at 13:45 then.",
                    time = "10:33",
                    isFromCurrentUser = false
                )
            )

            isLoading = false
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            // simuliert Nachricht senden
            delay(500.milliseconds)

            val newMessage = ChatMessage(
                id = System.currentTimeMillis().toString(),
                senderName = "Me",
                text = text,
                time = "now",
                isFromCurrentUser = true
            )

            messages = messages + newMessage
        }
    }
}