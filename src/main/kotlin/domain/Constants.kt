package domain

const val MainSplitter = "###"

fun getBindMessage(vararg msg: String): String {
    return msg.joinToString(MainSplitter)
}

fun String.getMessageAt(index: Int): String {
    return split(MainSplitter)[index]
}

fun String.asMessage(serverId: String, serverIp: String): String {
    return getBindMessage("CHAT", serverId, serverIp, this.replace("#", " "))
}