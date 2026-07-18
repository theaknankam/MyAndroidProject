package ui_elemente.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsharing_app.data.AppDatabase
import com.example.carsharing_app.data.ChatMessageEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ui_elemente.model.ChatMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Chat ID zwischen zwei Nutzern (für jetzt: globaler Chat)
    private var chatId = "global_chat"

    private val _messages = MutableStateFlow<List<ChatMessageEntity>>(emptyList())
    val messages: StateFlow<List<ChatMessageEntity>> = _messages
    fun initChat(otherUserId: String) {  // ← neu
        val currentUserId = auth.currentUser?.uid ?: return
        chatId = if (currentUserId < otherUserId) {
            "${currentUserId}_${otherUserId}"
        } else {
            "${otherUserId}_${currentUserId}"
    }
        // ← Chat Dokument mit participants erstellen
        db.collection("chats").document(chatId).set(
            hashMapOf(
                "participants" to listOf(currentUserId, otherUserId),
                "otherUserEmail" to (auth.currentUser?.email ?: "")
            ),
            com.google.firebase.firestore.SetOptions.merge()
        )

        loadMessages()
    }


    private fun loadMessages() {
        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    _messages.value = snapshot.documents.mapNotNull { doc ->
                        ChatMessageEntity(
                            id = doc.id,
                            senderId = doc.getString("senderId") ?: "",
                            senderName = doc.getString("senderName") ?: "",
                            text = doc.getString("text") ?: "",
                            time = doc.getString("time") ?: "",
                            timestamp = doc.getLong("timestamp") ?: 0,
                            isFromCurrentUser = doc.getString("senderId") == auth.currentUser?.uid                        )
                    }
                }
            }
    }
    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val userId = auth.currentUser?.uid ?: return
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .add(ChatMessageEntity(
                senderId = userId,
                senderName = auth.currentUser?.email ?: "User",
                text = text,
                time = time,
                timestamp = System.currentTimeMillis()
            ))
        // ← lastMessage updaten damit ChatListScreen es anzeigt
        db.collection("chats").document(chatId).update(
            mapOf(
                "lastMessage" to text,
                "lastMessageTime" to time
            )
        )
    }
}



    /*val messages: StateFlow<List<ChatMessage>> = _messages
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
}*/