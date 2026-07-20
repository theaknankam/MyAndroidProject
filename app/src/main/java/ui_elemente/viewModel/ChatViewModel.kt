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

/**
 * ViewModel für den individuellen Chat zwischen zwei Nutzern.
 * Verwaltet das Senden und Empfangen von Nachrichten in Echtzeit via Firestore.
 */
class ChatViewModel : ViewModel() {

    // Firebase Instanzen für Datenbankzugriff und Authentifizierung
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Die ID des aktuellen Chats (wird in initChat generiert)
    private var chatId = "global_chat"

    // StateFlow für die Liste der Nachrichten, die in der UI angezeigt wird
    private val _messages = MutableStateFlow<List<ChatMessageEntity>>(emptyList())
    val messages: StateFlow<List<ChatMessageEntity>> = _messages

    /**
     * Initialisiert den Chat mit einem anderen Nutzer.
     * Generiert eine eindeutige Chat-ID basierend auf beiden User-IDs.
     */
    fun initChat(otherUserId: String) {
        val currentUserId = auth.currentUser?.uid ?: return
        val myEmail = auth.currentUser?.email ?: "User"
        
        // Eindeutige ID erstellen: Immer die kleinere ID zuerst, damit beide Nutzer 
        // im selben "Raum" landen (z.B. "A_B" ist dasselbe wie "B_A").
        chatId = if (currentUserId < otherUserId) {
            "${currentUserId}_${otherUserId}"
        } else {
            "${otherUserId}_${currentUserId}"
        }
        
        // Chat-Metadaten in Firestore sicherstellen (Teilnehmerliste)
        // Wir speichern auch unseren eigenen Namen in einer Map, damit der Partner
        // uns in seiner Chat-Liste identifizieren kann.
        val chatData = hashMapOf(
            "participants" to listOf(currentUserId, otherUserId),
            "names" to mapOf(currentUserId to myEmail),
            "lastActive" to System.currentTimeMillis()
        )
        
        // "Set" mit Merge-Option: Fügt neue Felder hinzu oder aktualisiert sie,
        // ohne andere Teilnehmer-Daten in der 'names' Map zu löschen.
        db.collection("chats").document(chatId).set(chatData, com.google.firebase.firestore.SetOptions.merge())

        // Echtzeit-Überwachung der Nachrichten starten
        loadMessages()
    }

    /**
     * Setzt einen Snapshot-Listener auf die Nachrichten-Subcollection des Chats.
     * Aktualisiert automatisch _messages, wenn neue Daten eintreffen.
     */
    private fun loadMessages() {
        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp") // Nachrichten chronologisch sortieren
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                
                if (snapshot != null) {
                    // Firestore-Dokumente in lokale ChatMessageEntity Objekte umwandeln
                    _messages.value = snapshot.documents.mapNotNull { doc ->
                        ChatMessageEntity(
                            id = 0, // ID für Room (hier nicht primär genutzt)
                            senderId = doc.getString("senderId") ?: "",
                            senderName = doc.getString("senderName") ?: "User",
                            text = doc.getString("text") ?: "",
                            time = doc.getString("time") ?: "",
                            timestamp = doc.getLong("timestamp") ?: 0,
                            // Prüfen, ob die Nachricht vom aktuell eingeloggten User kommt
                            isFromCurrentUser = doc.getString("senderId") == auth.currentUser?.uid
                        )
                    }
                }
            }
    }

    /**
     * Sendet eine Nachricht an den Chat-Partner.
     */
    fun sendMessage(text: String) {
        if (text.isBlank()) return // Leere Nachrichten ignorieren

        val userId = auth.currentUser?.uid ?: return
        val userName = auth.currentUser?.email?.substringBefore("@") ?: "User"
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        // Nachrichten-Objekt für Firestore
        val message = hashMapOf(
            "senderId" to userId,
            "senderName" to userName,
            "text" to text,
            "time" to time,
            "timestamp" to System.currentTimeMillis()
        )

        // Nachricht in der Unter-Kollektion "messages" speichern
        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .add(message)
            
        // Den Haupt-Chat-Eintrag aktualisieren für die Vorschau in der Liste
        db.collection("chats").document(chatId).update(
            mapOf(
                "lastMessage" to text,
                "lastMessageTime" to time,
                "lastActive" to System.currentTimeMillis()
            )
        )
    }
}
