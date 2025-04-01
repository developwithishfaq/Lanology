package domain.usecases

import data.local.getJson
import data.persistence.TextFileHandler
import domain.models.ChatModel

class GetAllChatsFromLocal {
    operator fun invoke(): List<ChatModel> {
        val fileHandler = TextFileHandler("chats.txt")
        val allChatsStr = fileHandler.readText()
        return if (allChatsStr.isNotBlank()) {
            getJson().decodeFromString<List<ChatModel>>(allChatsStr)
        } else {
            listOf()
        }
    }
}