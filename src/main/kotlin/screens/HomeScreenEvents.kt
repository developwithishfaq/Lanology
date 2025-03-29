package screens

sealed class HomeScreenEvents {
    data object StartFindingServers : HomeScreenEvents()
    data class SendMessage(val ip: String) : HomeScreenEvents()
}