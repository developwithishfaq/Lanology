package data.local

import domain.models.ServerModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update

class ServersManager {

    private val _servers = MutableStateFlow<List<ServerModel>>(emptyList())
    val servers = _servers.asStateFlow()


    fun getServerById(id: String): ServerModel? {
        println("getServerById Called")
        println("Servers(${servers.value.size}) Id($id)\nList=${servers.value}")
        return servers.value.firstOrNull { it.serverId == id }
    }

    fun addServer(serverIp: String, serverName: String, serverId: String) {
        val list = servers.value.toMutableList()
        val index = list.indexOfFirst {
            it.serverId == serverId
        }
        if (index == -1) {
            println("----------------------------")
            println("Server Ip=${serverIp}\nName=${serverName}\nMy Ip=${serverIp}")
            println("----------------------------")
            list.add(
                ServerModel(serverName, serverIp, serverId)
            )
        }
        _servers.update {
            list
        }
    }

}