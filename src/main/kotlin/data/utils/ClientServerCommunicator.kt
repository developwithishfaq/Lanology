package data.utils

import data.local.ServersManager
import domain.MainSplitter
import domain.getMessageAt
import domain.models.ChatModel
import domain.usecases.SendChatMessage
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
    private val sendChatMessage: SendChatMessage
) {


    fun init() {
        startListening()
        serverFinderUtil.startBroadcasting()
    }

    /*
        fun sendMessage(targetIp: String, message: String) {
            println("sendMessage:targetIp=${targetIp},message=${message}")
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val socket = Socket(targetIp, CLIENT_PORT)
                    val writer = OutputStreamWriter(socket.getOutputStream())
                    writer.write(message)
                    writer.flush()
                    socket.close()
                } catch (e: Exception) {
                    println("Failed to send message: ${e.message}")
                }
            }
        }*/

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
                    sendChatMessage.addChatModel(
                        ChatModel(
                            message = chat,
                            serverName = serverModel.serverName,
                            serverId = serverModel.serverId,
                            messageType = 0,
                            serverIp = serverModel.serverIp,
                            isSentByMe = false
                        )
                    )
                }
            }
            clientSocket.close()
        }
    }
}