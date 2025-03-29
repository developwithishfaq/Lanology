package domain

const val MainSplitter = "###"

fun getBindMessage(vararg msg: String): String {
    return msg.joinToString(MainSplitter)
}

fun String.getMessageAt(index: Int): String {
    return split(MainSplitter)[index]
}