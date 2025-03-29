package screens.home

sealed class HomeScreenEvents {
    data object ShowSignIn : HomeScreenEvents()
    data class SendMessage(val ip: String) : HomeScreenEvents()
}