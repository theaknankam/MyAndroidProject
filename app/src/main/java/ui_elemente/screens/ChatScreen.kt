package ui_elemente.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ui_elemente.components.ChatBubble
import ui_elemente.components.ChatInputBar
import ui_elemente.navigation.Topbar
import ui_elemente.viewModel.ChatViewModel

/**
 * Der Bildschirm für eine private 1-zu-1 Konversation.
 * Dieser Screen wird aufgerufen, wenn man in der RideDetails auf "Chat" klickt
 * oder einen bestehenden Chat in der Nachrichten-Liste auswählt.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavHostController,
    driverId: String = "" // Die ID des Empfängers
) {
    // ViewModel zur Verwaltung der Geschäftslogik
    val viewModel: ChatViewModel = viewModel()

    // Status für das Textfeld (Eingabe)
    var messageText by remember { mutableStateOf("") }
    
    // Die Nachrichtenliste aus dem ViewModel abonnieren
    val messages by viewModel.messages.collectAsState()

    // Initialisierung: Chat-ID basierend auf Teilnehmern berechnen
    LaunchedEffect(driverId) {
        if (driverId.isNotEmpty()) {
            viewModel.initChat(driverId)
        }
    }

    Scaffold(
        topBar = {
            Column {
                Topbar("Chat", navController)
                
                // Status-Leiste unter der Topbar
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Grüner Punkt zur Visualisierung (Simulation)
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color(0xFF4CAF50), shape = androidx.compose.foundation.shape.CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Direct Message",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        },
        bottomBar = {
            // Komponente für das Tippen und Absenden
            ChatInputBar(
                messageText = messageText,
                onMessageChange = { messageText = it },
                onSendClick = {
                    if (messageText.isNotBlank()) {
                        viewModel.sendMessage(messageText)
                        messageText = "" // Feld nach dem Senden leeren
                    }
                }
            )
        }
    ) { paddingValues ->
        // Nachrichtenverlauf anzeigen
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFFAFAFA)), // Heller Hintergrund
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Jede Nachricht wird als ChatBubble gerendert
            items(messages) { message ->
                ChatBubble(message = message)
            }
        }
    }
}
