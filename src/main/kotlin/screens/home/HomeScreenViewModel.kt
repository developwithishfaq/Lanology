package screens.home

import data.local.LocalPrefs
import data.utils.ClientServerCommunicator
import data.utils.ServerFinderUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeScreenState(
    val msg: String = "",
    val showSignInScreen: Boolean = true
)

class HomeScreenViewModel(
    private val prefs: LocalPrefs = LocalPrefs(),
    private val serverFinderUtil: ServerFinderUtil = ServerFinderUtil(prefs),
    private val messageSender: ClientServerCommunicator = ClientServerCommunicator()
) {
    val servers = serverFinderUtil.servers

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                showSignInScreen = prefs.userName.isBlank()
            )
        }
    }

    fun onEvent(events: HomeScreenEvents) {
        when (events) {

            is HomeScreenEvents.SendMessage -> {
                messageSender.sendMessage(events.ip, "Hi From Client")
            }

            HomeScreenEvents.ShowSignIn -> {
                _state.update { it.copy(showSignInScreen = it.showSignInScreen.not()) }
            }
        }
    }

    fun setUserName(userName: String) {
        prefs.userName = userName
    }

    fun startServices() {
        messageSender.init()
    }

}