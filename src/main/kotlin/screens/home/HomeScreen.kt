package screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {
    val state by viewModel.state.collectAsState()
    val servers by viewModel.servers.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.startServices()
    }

    Column {
        TextField(
            value = state.msg,
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = {
                servers.firstOrNull()?.let {
                    viewModel.onEvent(HomeScreenEvents.SendMessage(it.serverIp))
                }
            }
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(top = 30.dp)
        ) {
            items(servers) { server ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Column {
                        Text(server.serverName)
                        Text(server.serverIp)
                    }
                }
            }
        }
    }

}