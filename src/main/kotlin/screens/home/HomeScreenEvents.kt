package screens.home

sealed class HomeScreenEvents {
    data object ShowSignIn : HomeScreenEvents()
    data class SendMessage(val ip: String,val id: String, val msg: String) : HomeScreenEvents()
    data class OnMessageChange(val msg: String) : HomeScreenEvents()
}