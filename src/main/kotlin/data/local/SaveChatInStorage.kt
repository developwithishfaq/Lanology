package data.local

import data.persistence.TextFileHandler
import domain.models.ChatModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class SaveChatInStorage(
) {
    operator fun invoke(model: ChatModel) {
        val fileHandler = TextFileHandler("chats.txt")
        val allChatsStr = fileHandler.readText()
        val allChatsList = if (allChatsStr.isNotBlank()) {
            getJson().decodeFromString<List<ChatModel>>(allChatsStr)
        } else {
            listOf()
        }.toMutableList()
        allChatsList.add(model)
        val newChatsList = getJson().encodeToString(allChatsList)
        fileHandler.writeText(newChatsList)
    }
}

@OptIn(ExperimentalSerializationApi::class)
fun getJson(): Json {
    return Json {
        this.ignoreUnknownKeys = true
        explicitNulls = true
    }
}