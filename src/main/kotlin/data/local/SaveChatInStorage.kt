package data.local

import data.persistence.TextFileHandler
import domain.models.ChatModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class SaveChatInStorage(
) {
    operator fun invoke(chatModel: ChatModel) {
        val fileHandler = TextFileHandler("chats.txt")
        val model = getJson().encodeToString(chatModel)
        if (fileHandler.readText().isBlank()) {
            fileHandler.writeText(model)
        } else {
            fileHandler.appendText("\n$model")
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
fun getJson(): Json {
    return Json {
        this.ignoreUnknownKeys = true
        explicitNulls = true
    }
}