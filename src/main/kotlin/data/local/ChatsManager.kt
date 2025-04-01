package data.local

import domain.models.ChatModel
import domain.usecases.GetAllChatsFromLocal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatsManager(
    private val getAllChatsFromLocal: GetAllChatsFromLocal,
    private val serversManager: ServersManager
) {

    private val _chats = MutableStateFlow<List<ChatModel>>(emptyList())
    val chats = _chats.asStateFlow()

    init {
        val prevChats = getAllChatsFromLocal.invoke()
        prevChats.forEach { chat ->
            serversManager.addServer(
                serverName = chat.serverName,
                serverId = chat.serverId,
                serverIp = chat.serverIp,
                isOnline = false
            )
        }
        _chats.update { prevChats }
    }


    fun addChat(model: ChatModel) {
        val list = chats.value.toMutableList()
        list.add(model)
        println("Chat Added $model\nSize=${list.size}")
        _chats.update { list }
    }

}