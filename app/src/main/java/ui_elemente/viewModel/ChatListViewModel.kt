package ui_elemente.viewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ui_elemente.model.ChatPreview

class ChatListViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _chats = MutableStateFlow<List<ChatPreview>>(emptyList())
    val chats: StateFlow<List<ChatPreview>> = _chats

    init {
        loadChats()
    }

    private fun loadChats() {
        val currentUserId = auth.currentUser?.uid ?: return

        db.collection("chats")
            .whereArrayContains("participants", currentUserId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    _chats.value = snapshot.documents.mapNotNull { doc ->
                        val participants = doc.get("participants") as? List<String>
                            ?: return@mapNotNull null
                        val otherUserId = participants.find { it != currentUserId }
                            ?: return@mapNotNull null
                        ChatPreview(
                            chatId = doc.id,
                            otherUserId = otherUserId,
                            otherUserEmail = doc.getString("otherUserEmail") ?: otherUserId,
                            lastMessage = doc.getString("lastMessage") ?: "",
                            lastMessageTime = doc.getString("lastMessageTime") ?: ""
                        )
                    }
                }
            }
    }
}