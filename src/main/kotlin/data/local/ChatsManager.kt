package data.local

import domain.models.ChatModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatsManager(
    private val prefs: LocalPrefs
) {

    private val _chats = MutableStateFlow<List<ChatModel>>(emptyList())
    val chats = _chats.asStateFlow()


    fun addChat(model: ChatModel) {
        val list = chats.value.toMutableList()
        list.add(model)
        println("Chat Added $model\nSize=${list.size}")
        _chats.update { list }
    }

}