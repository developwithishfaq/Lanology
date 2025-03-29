package data.utils

import data.local.LocalPrefs
import domain.getBindMessage
import domain.getMessageAt
import domain.models.ServerModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class ServerFinderUtil(
    private val prefs: LocalPrefs
) {

    private val _servers = MutableStateFlow<List<ServerModel>>(emptyList())
    val servers = _servers.asStateFlow()
    private var ipAddress: String? = null

    fun startFinding() {
        startListeningForServers()
    }

    private fun startListeningForServers() {
        CoroutineScope(Dispatchers.IO).launch {
            val socket = DatagramSocket(9999, InetAddress.getByName("0.0.0.0"))
            socket.broadcast = true
            val buffer = ByteArray(256)
            while (true) {
                val packet = DatagramPacket(buffer, buffer.size)
                socket.receive(packet)
                val message = String(packet.data, 0, packet.length)
                if (message.startsWith("CHAT_SERVER")) {
                    println("Message=${message}")
                    val serverIp = message.getMessageAt(1)
                    val serverName = message.getMessageAt(2)
                    addServer(serverIp, serverName)
                }
            }
        }
    }

    private fun addServer(serverIp: String, serverName: String) {
        if (serverIp == ipAddress) {
            return
        }
        val list = servers.value.toMutableList()
        val index = list.indexOfFirst {
            it.serverIp == serverIp
        }
        if (index == -1) {
            list.add(
                ServerModel(serverName, serverIp)
            )
        }
        _servers.update {
            list
        }
    }

    fun startBroadcasting() {
        GlobalScope.launch {
            val socket = DatagramSocket()
            val broadcastAddress = InetAddress.getByName("255.255.255.255")
            ipAddress = InetAddress.getLocalHost().hostAddress
            startFinding()
            println("Ip Address $ipAddress")
            while (true) {
                val message = getBindMessage(
                    "CHAT_SERVER",
                    ipAddress!!,
                    prefs.userName,
                    prefs.getDeskId()
                )
                val packet = DatagramPacket(message.toByteArray(), message.length, broadcastAddress, 9999)
                socket.send(packet)
                println("Broadcasting server presence...")
                delay(5000)
            }
        }
    }

}