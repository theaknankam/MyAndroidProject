package ui_elemente.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ui_elemente.components.ChatListItem
import ui_elemente.navigation.Topbar
import ui_elemente.viewModel.ChatListViewModel

/**
 * Übersicht aller aktiven Chats des Nutzers.
 */
@Composable
fun ChatListScreen(
    navController: NavHostController,
    viewModel: ChatListViewModel = viewModel()
) {
    // Liste aller Chats aus dem Firestore abrufen
    val chats by viewModel.chats.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Einheitliche obere Leiste
        Topbar("Messages", navController)

        // Falls keine Chats vorhanden sind, Platzhalter anzeigen
        if (chats.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No active conversations", color = Color.Gray)
            }
        } else {
            // Liste der Konversationen
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(chats) { chat ->
                    // Einzelnes Element in der Liste (Vorschau)
                    ChatListItem(
                        chat = chat,
                        onClick = {
                            // Beim Klick zum spezifischen Chat navigieren
                            navController.navigate("chat/${chat.otherUserId}")
                        }
                    )
                }
            }
        }
    }
}
