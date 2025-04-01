package screens.home

import data.local.ChatsManager
import data.local.LocalPrefs
import data.local.ServersManager
import data.utils.ClientServerCommunicator
import data.utils.ServerFinderUtil
import domain.asMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeScreenState(
    val msg: String = "",
    val myId: String = "",
    val showSignInScreen: Boolean = true
)

class HomeScreenViewModel(
    private val prefs: LocalPrefs = LocalPrefs(),
    private val serversManager: ServersManager = ServersManager(),
    private val serverFinderUtil: ServerFinderUtil = ServerFinderUtil(prefs, serversManager),
    private val chatsManager: ChatsManager = ChatsManager(prefs),
    private val messageSender: ClientServerCommunicator = ClientServerCommunicator(
        serverFinderUtil = serverFinderUtil,
        chatsManager = chatsManager,
        serversManager = serversManager,
        prefs = prefs
    )
) {

    val servers = serversManager.servers
    val chats = chatsManager.chats

    private val _state = MutableStateFlow(
        HomeScreenState(
            myId = prefs.getDeskId()
        )
    )
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
                messageSender.sendMessage(
                    targetIp = events.ip,
                    message = events.msg.asMessage(serverId = prefs.getDeskId(), serverIp = events.ip)
                )
            }

            HomeScreenEvents.ShowSignIn -> {
                _state.update { it.copy(showSignInScreen = it.showSignInScreen.not()) }
            }

            is HomeScreenEvents.OnMessageChange -> {
                _state.update {
                    it.copy(
                        msg = events.msg
                    )
                }
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