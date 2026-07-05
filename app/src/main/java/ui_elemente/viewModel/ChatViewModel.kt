package ui_elemente.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsharing_app.data.AppDatabase
import com.example.carsharing_app.data.ChatMessageEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ui_elemente.model.ChatMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val chatDao =
        AppDatabase.getDatabase(application).chatDao()

    val messages = chatDao.getAllMessages()
        .map { list ->
            list.map { entity ->
                ChatMessage(
                    id = entity.id.toString(),
                    senderName = entity.senderName,
                    text = entity.text,
                    time = entity.time,
                    isFromCurrentUser = entity.isFromCurrentUser
                )
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            chatDao.insertMessage(
                ChatMessageEntity(
                    senderName = "Me",
                    text = text,
                    time = getCurrentTime(),
                    isFromCurrentUser = true
                )
            )
        }
    }

    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault())
            .format(Date())
    }
}