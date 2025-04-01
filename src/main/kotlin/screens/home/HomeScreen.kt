package screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import screens.chat.ChatScreen

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {
    val state by viewModel.state.collectAsState()
    val servers by viewModel.servers.collectAsState()
    val chats by viewModel.chats.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.startServices()
    }

    Column {
        Text("Server Id = ${state.myId}")
        ChatScreen(servers = servers, chats = chats, onSendMessage = { ip, id, msg ->
            viewModel.onEvent(HomeScreenEvents.SendMessage(ip, id, msg))
        })
    }

}