package data.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket

class ClientServerCommunicator(
    private val serverBroadcaster: ServerBroadcaster = ServerBroadcaster()
) {
    private val CLIENT_PORT = 4001


    init {
        startListening()
        serverBroadcaster.startBroadcasting()
    }


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
            clientSocket.close()
        }
    }
}