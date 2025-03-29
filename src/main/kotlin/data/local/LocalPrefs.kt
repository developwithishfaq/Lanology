package data.local

import java.util.prefs.Preferences

class LocalPrefs {
    private val prefs = Preferences.userRoot().node(this::class.java.name)

    private fun generateRandomId(): Long {
        return (10000000L..99999999L).random()
    }

    fun getDeskId(): String {
        val id = getLong("easyDeskId", -1)
        if (id == -1L) {
            val newId = generateRandomId()
            setLong("easyDeskId", newId)
            return newId.toString()
        }
        return id.toString()
    }

    var userName: String
        get() = getString("userName", "")
        set(value) = setString("userName", value)

    var autoAccept: Boolean
        get() = getBoolean("autoAccept", false)
        set(value) = setBoolean("autoAccept", value)

    var shareHiddenFiles: Boolean
        get() = getBoolean("shareHiddenFiles", true)
        set(value) = setBoolean("shareHiddenFiles", value)

    var canDeleteFiles: Boolean
        get() = getBoolean("canDeleteFiles", false)
        set(value) = setBoolean("canDeleteFiles", value)

    private fun setString(key: String, value: String) {
        prefs.put(key, value)
    }

    private fun getString(key: String, defaultValue: String = ""): String {
        return prefs.get(key, defaultValue)
    }

    private fun setInt(key: String, value: Int) {
        prefs.putInt(key, value)
    }

    private fun getInt(key: String, defaultValue: Int = 0): Int {
        return prefs.getInt(key, defaultValue)
    }

    private fun setBoolean(key: String, value: Boolean) {
        prefs.putBoolean(key, value)
    }

    private fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    private fun setLong(key: String, value: Long) {
        prefs.putLong(key, value)
    }

    private fun getLong(key: String, defaultValue: Long = 0): Long {
        return prefs.getLong(key, defaultValue)
    }
}