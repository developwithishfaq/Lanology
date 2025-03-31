package screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.models.fakeChats
import domain.models.fakeServers
import screens.chat.ChatScreen

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {
    val servers by viewModel.servers.collectAsState()
    val chats by viewModel.chats.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.startServices()
    }

    Column {
        ChatScreen(servers = servers, chats = chats, onSendMessage = { ip, id, msg ->
            viewModel.onEvent(HomeScreenEvents.SendMessage(ip, id, msg))
        })
    }

}