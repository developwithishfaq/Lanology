package data.local

import domain.models.ServerModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ServersManager {

    private val _servers = MutableStateFlow<List<ServerModel>>(emptyList())
    val servers = _servers.asStateFlow()


    fun addServer(serverModel: ServerModel) {
        val servers = servers.value.toMutableList()
    }

}