package screens.chat

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.MyColors
import domain.models.ChatModel
import domain.models.ServerModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(
    servers: List<ServerModel>,
    chats: List<ChatModel>,
    onSendMessage: (String, String, String) -> Unit
) {
    var selectedServer by remember { mutableStateOf<ServerModel?>(null) }
    val chatScrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {// Sidebar for users list
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp)
                .background(Color(0xFFF5F5F5)) // Softer background for a modern feel
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Users",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)

            servers.forEach { server ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .clip(RoundedCornerShape(8.dp)) // Rounded corners for smooth UI
                        .clickable { selectedServer = server }
                        .background(
                            if (selectedServer == server) MyColors.Green else Color.Transparent
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                        .hoverable(remember { MutableInteractionSource() })
                        .pointerMoveFilter(
                            onEnter = { true },
                            onExit = { false }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person, // Icon for a user
                        contentDescription = "User",
                        tint = if (selectedServer == server) Color.White else Color(0xFF555555),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = server.serverName,
                        color = if (selectedServer == server) Color.White else Color(0xFF333333),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }


        // Chat area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9F9F9)) // Light gray background for a modern look
        ) {
            selectedServer?.let { server ->
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /*Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Chat Icon",
                        tint = Color(0xFF007AFF),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))*/
                    Text(
                        text = "Chat with ${server.serverName}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                }
                Divider(color = Color(0xFFE0E0E0))

                // Chat Messages
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .verticalScroll(chatScrollState)
                ) {
                    chats.filter { it.serverId == server.serverId }.forEach { chat ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (chat.isSentByMe) Arrangement.End else Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (chat.isSentByMe) Color(0xFF007AFF) else Color(0xFFECECEC),
                                        shape = RoundedCornerShape(
                                            topStart = 12.dp,
                                            topEnd = 12.dp,
                                            bottomStart = if (chat.isSentByMe) 12.dp else 0.dp,
                                            bottomEnd = if (chat.isSentByMe) 0.dp else 12.dp
                                        )
                                    )
                                    .padding(12.dp)
                                    .widthIn(min = 60.dp, max = 300.dp),
                            ) {
                                Text(
                                    text = chat.message,
                                    color = if (chat.isSentByMe) Color.White else Color.Black,
                                    fontSize = 16.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }

                // Chat Input
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var message by remember { mutableStateOf("") }

                    TextField(
                        value = message,
                        onValueChange = { message = it },
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White, shape = RoundedCornerShape(24.dp))
                            .padding(horizontal = 6.dp),
                        placeholder = { Text("Type a message...", color = Color.Gray) },
                        shape = RoundedCornerShape(24.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color(0xFFF5F5F5),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            onSendMessage.invoke(server.serverIp, server.serverId, message)
                            message = ""
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007AFF)),
                        elevation = ButtonDefaults.elevation(4.dp)
                    ) {
                        Icon(Icons.Filled.Send, contentDescription = "Send", tint = Color.White)
                    }
                }
            } ?: run {
                // No chat selected
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Select a server to start chatting",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

    }
}

