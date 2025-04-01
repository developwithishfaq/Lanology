package domain.usecases

import data.local.ChatsManager
import data.local.LocalPrefs
import data.utils.ClientServerCommunicator
import domain.asMessage
import domain.models.ChatModel

class SendChatMessage(
    private val chatsManager: ChatsManager,
    private val messageSender: ClientServerCommunicator,
    private val prefs: LocalPrefs
) {

    operator fun invoke(message: String, targetServerId: String, targetServerIp: String) {
        chatsManager.addChat(
            ChatModel(
                message = message,
                serverName = prefs.userName,
                serverId = targetServerId,
                messageType = 0,
                serverIp = targetServerIp,
                isSentByMe = true
            )
        )
        messageSender.sendMessage(
            targetIp = targetServerIp,
            message = message.asMessage(serverId = prefs.getDeskId(), serverIp = targetServerIp)
        )
    }
}