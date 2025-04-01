package domain.usecases

import data.local.ChatsManager
import data.local.LocalPrefs
import data.local.SaveChatInStorage
import domain.asMessage
import domain.models.ChatModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SendChatMessage(
    private val chatsManager: ChatsManager,
    private val messageSender: SendMessageToServer,
    private val prefs: LocalPrefs,
    private val saveChatInStorage: SaveChatInStorage
) {

    fun addChatModel(model: ChatModel) {
        chatsManager.addChat(model)
        saveChatInStorage.invoke(model)
        CoroutineScope(Dispatchers.IO).launch {
            messageSender.invoke(
                targetIp = model.serverIp,
                message = model.message.asMessage(serverId = prefs.getDeskId(), serverIp = model.serverIp)
            )
        }
    }

    operator fun invoke(message: String, targetServerId: String, targetServerIp: String) {
        val model = ChatModel(
            message = message,
            serverName = prefs.userName,
            serverId = targetServerId,
            messageType = 0,
            serverIp = targetServerIp,
            isSentByMe = true
        )
        addChatModel(model)
    }
}