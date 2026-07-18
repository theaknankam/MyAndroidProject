package ui_elemente.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.foundation.lazy.items

@Composable
fun ChatListScreen(
    navController: NavHostController,
    viewModel: ChatListViewModel = viewModel()
) {
    val chats by viewModel.chats.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Topbar("Messages", navController)

        if (chats.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No messages yet", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(chats) { chat ->
                    ChatListItem(
                        chat = chat,
                        onClick = {
                            navController.navigate("chat/${chat.otherUserId}")
                        }
                    )
                }
            }
        }
    }
}