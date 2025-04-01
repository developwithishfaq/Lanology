package data.utils

import data.local.ChatsManager
import data.local.SaveChatInStorage
import data.local.ServersManager
import domain.MainSplitter
import domain.getMessageAt
import domain.models.ChatModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

val CLIENT_PORT = 4001

class ClientServerCommunicator(
    private val serverFinderUtil: ServerFinderUtil,
    private val serversManager: ServersManager,
    private val chatsManager: ChatsManager,
    private val saveChatInStorage: SaveChatInStorage
) {


    fun init() {
        startListening()
        serverFinderUtil.startBroadcasting()
    }

    private fun startListening() {
        CoroutineScope(Dispatchers.IO).launch {
            val serverSocket = ServerSocket(CLIENT_PORT)
            println("Listening for messages on port $CLIENT_PORT...")
            while (true) {
                val clientSocket = serverSocket.accept()
                handleIncomingMessage(clientSocket)
            }
        }
    }

    private fun handleIncomingMessage(clientSocket: Socket) {
        CoroutineScope(Dispatchers.IO).launch {
            val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            val message = reader.readLine()
            println("Received: $message")
            if (message.startsWith("CHAT$MainSplitter")) {
                val serverId = message.getMessageAt(1)
                val chat = message.getMessageAt(3)
                val serverModel = serversManager.getServerById(serverId)
                println("serverModel is $serverModel")
                if (serverModel != null) {
                    val model = ChatModel(
                        message = chat,
                        serverName = serverModel.serverName,
                        serverId = serverModel.serverId,
                        messageType = 0,
                        serverIp = serverModel.serverIp,
                        isSentByMe = false
                    )
                    saveChatInStorage.invoke(model)
                    chatsManager.addChat(model)
                }
            }
            clientSocket.close()
        }
    }
}