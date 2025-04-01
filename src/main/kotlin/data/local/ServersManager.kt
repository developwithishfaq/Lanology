package data.local

import domain.models.ServerModel
import domain.usecases.GetAllChatsFromLocal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ServersManager(
    private val getAllChatsFromLocal: GetAllChatsFromLocal
) {

    private val _servers = MutableStateFlow<List<ServerModel>>(emptyList())
    val servers = _servers.asStateFlow()

    fun getServerById(id: String): ServerModel? {
        println("getServerById Called")
        println("Servers(${servers.value.size}) Id($id)\nList=${servers.value}")
        return servers.value.firstOrNull { it.serverId == id }
    }

    fun updateStatus(serverId: String, isOnline: Boolean) {
        servers.value.firstOrNull { it.serverId == serverId }?.let { serverModel ->
            addServer(
                serverId = serverModel.serverId,
                serverIp = serverModel.serverIp,
                serverName = serverModel.serverName,
                isOnline = isOnline
            )
        }
    }

    fun addServer(serverIp: String, serverName: String, serverId: String, isOnline: Boolean) {
        val list = servers.value.toMutableList()
        val index = list.indexOfFirst {
            it.serverId == serverId
        }
        if (index == -1) {
            println("----------------------------")
            println("Server Ip=${serverIp}\nName=${serverName}\nMy Ip=${serverIp}")
            println("----------------------------")
            list.add(
                ServerModel(serverName = serverName, serverIp = serverIp, serverId = serverId, isOnline = isOnline)
            )
        } else {
            list[index] =
                ServerModel(serverName = serverName, serverIp = serverIp, serverId = serverId, isOnline = isOnline)
        }
        _servers.update {
            list
        }
    }

}