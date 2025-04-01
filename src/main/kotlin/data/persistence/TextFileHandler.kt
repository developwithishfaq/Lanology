package data.persistence

import java.io.File

class TextFileHandler(private val filePath: String) {
    private val file = File(filePath)

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    /**
     * Writes text to the file, overwriting existing content.
     */
    fun writeText(text: String) {
        file.writeText(text)
    }

    /**
     * Appends text to the existing file content.
     */
    fun appendText(text: String) {
        file.appendText(text)
    }

    /**
     * Reads the content of the file.
     */
    fun readText(): String {
        return file.readText()
    }
}
