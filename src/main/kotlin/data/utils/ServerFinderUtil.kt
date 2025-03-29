package data.utils

import domain.getMessageAt
import domain.models.ServerModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class ServerFinderUtil {

    private val _servers = MutableStateFlow<List<ServerModel>>(emptyList())
    val servers = _servers.asStateFlow()

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

}