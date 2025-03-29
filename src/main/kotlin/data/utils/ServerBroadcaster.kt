package data.utils

import domain.MainSplitter
import domain.getBindMessage
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.*

class ServerBroadcaster {

    fun startBroadcasting() {
        GlobalScope.launch {
            val socket = DatagramSocket()
            val broadcastAddress = InetAddress.getByName("255.255.255.255")
            while (true) {
                val message = getBindMessage("CHAT_SERVER", InetAddress.getLocalHost().hostAddress, "Ishfaq")
                val packet = DatagramPacket(message.toByteArray(), message.length, broadcastAddress, 9999)
                socket.send(packet)
                println("Broadcasting server presence...")
                delay(5000)
            }
        }
    }
}
