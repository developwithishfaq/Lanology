package domain.models

data class ServerModel(
    val serverName: String,
    val serverIp: String,
    val serverId: String,
    val isOnline: Boolean = true,
)

val fakeServers = listOf(
    ServerModel("Alice", "192.168.1.10", "srv-001"),
    ServerModel("Bob", "192.168.1.11", "srv-002"),
    ServerModel("Charlie", "192.168.1.12", "srv-003"),
    ServerModel("David", "192.168.1.13", "srv-004"),
    ServerModel("Eve", "192.168.1.14", "srv-005")
)
