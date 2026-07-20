package ui_elemente.viewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ui_elemente.model.ChatPreview

/**
 * ViewModel für die Nachrichten-Übersicht (Chat-Liste).
 * Lädt alle aktiven Konversationen des aktuellen Nutzers.
 */
class ChatListViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Liste der Chat-Vorschauen für die UI
    private val _chats = MutableStateFlow<List<ChatPreview>>(emptyList())
    val chats: StateFlow<List<ChatPreview>> = _chats

    init {
        // Sofort beim Erstellen die Chats laden
        loadChats()
    }

    /**
     * Sucht in der "chats" Kollektion nach Dokumenten, in denen der aktuelle User
     * in der "participants" Liste steht.
     */
    private fun loadChats() {
        val currentUserId = auth.currentUser?.uid ?: return

        db.collection("chats")
            // Firestore Query: Finde Dokumente, wo das Array 'participants' meine ID enthält
            .whereArrayContains("participants", currentUserId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                
                if (snapshot != null) {
                    _chats.value = snapshot.documents.mapNotNull { doc ->
                        // Teilnehmer extrahieren, um den Partner zu finden
                        val participants = doc.get("participants") as? List<String>
                            ?: return@mapNotNull null
                        
                        // Die ID des anderen Nutzers (nicht meine eigene)
                        val otherUserId = participants.find { it != currentUserId }
                            ?: return@mapNotNull null
                        
                        // Vorschau-Daten zusammenbauen
                        ChatPreview(
                            chatId = doc.id,
                            otherUserId = otherUserId,
                            // Anzeige-Name (Email als Fallback)
                            otherUserEmail = doc.getString("otherUserEmail") ?: "User $otherUserId",
                            lastMessage = doc.getString("lastMessage") ?: "No messages yet",
                            lastMessageTime = doc.getString("lastMessageTime") ?: ""
                        )
                    }
                }
            }
    }
}
