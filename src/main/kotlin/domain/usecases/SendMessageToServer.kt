package domain.usecases

import data.utils.CLIENT_PORT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.Socket

class SendMessageToServer {

    suspend operator fun invoke(targetIp: String, message: String): Boolean {
        return withContext(Dispatchers.IO) {
            println("sendMessage:targetIp=${targetIp},message=${message}")
            try {
                val socket = Socket(targetIp, CLIENT_PORT)
                val writer = OutputStreamWriter(socket.getOutputStream())
                writer.write(message)
                writer.flush()
                socket.close()
                true
            } catch (e: Exception) {
                println("Failed to send message: ${e.message}")
                false
            }
        }
    }
}