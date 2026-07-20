package ui_elemente.viewModel

import androidx.lifecycle.ViewModel
import com.example.carsharing_app.data.ChatMessageEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private var chatId = "global_chat"

    private val _messages = MutableStateFlow<List<ChatMessageEntity>>(emptyList())
    val messages: StateFlow<List<ChatMessageEntity>> = _messages

    fun initChat(otherUserId: String) {
        val currentUserId = auth.currentUser?.uid ?: return
        chatId = if (currentUserId < otherUserId) {
            "${currentUserId}_${otherUserId}"
        } else {
            "${otherUserId}_${currentUserId}"
        }
        
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
                            id = 0, // Room will auto-generate this if we save it locally
                            senderId = doc.getString("senderId") ?: "",
                            senderName = doc.getString("senderName") ?: "",
                            text = doc.getString("text") ?: "",
                            time = doc.getString("time") ?: "",
                            timestamp = doc.getLong("timestamp") ?: 0,
                            isFromCurrentUser = doc.getString("senderId") == auth.currentUser?.uid
                        )
                    }
                }
            }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val userId = auth.currentUser?.uid ?: return
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        val message = hashMapOf(
            "senderId" to userId,
            "senderName" to (auth.currentUser?.email ?: "User"),
            "text" to text,
            "time" to time,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .add(message)
            
        db.collection("chats").document(chatId).update(
            mapOf(
                "lastMessage" to text,
                "lastMessageTime" to time
            )
        )
    }
}
