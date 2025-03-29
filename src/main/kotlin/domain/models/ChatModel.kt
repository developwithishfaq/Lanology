package domain.models

data class ChatModel(
    val message: String,
    val serverName: String,
    val serverIp: String,
    val serverId: String,
    val messageType: Int = 0,
    val isSentByMe: Boolean = false,
)
//MessageType
// 0 text
// 1 file

val fakeChats = listOf(
    ChatModel("Hey, how are you?", "Alice", "192.168.1.10", "srv-001"),
    ChatModel("I'm good, thanks! What about you?", "Bob", "192.168.1.11", "srv-002"),
    ChatModel("Working on the new project!", "Charlie", "192.168.1.12", "srv-003"),
    ChatModel("Let’s meet at 3 PM for the discussion.", "David", "192.168.1.13", "srv-004"),
    ChatModel("Sure, I'll be there.", "Eve", "192.168.1.14", "srv-005"),
    ChatModel("Can you review my code?", "Alice", "192.168.1.10", "srv-001"),
    ChatModel("Yes, I'll check and update you soon.", "Bob", "192.168.1.11", "srv-002"),
    ChatModel("I need access to the new repo.", "Charlie", "192.168.1.12", "srv-003"),
    ChatModel("Already shared it with you.", "David", "192.168.1.13", "srv-004"),
    ChatModel("Great, thanks!", "Eve", "192.168.1.14", "srv-005")
)
