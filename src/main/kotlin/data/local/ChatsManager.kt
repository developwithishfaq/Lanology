package data.local

import androidx.compose.runtime.MutableState
import data.utils.ServerFinderUtil
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
        list.add(
            model.copy(
                isSentByMe = prefs.getDeskId() == model.serverId
            )
        )
        println("Chat Added $model\nSize=${list.size}")
        _chats.update { list }
    }

}