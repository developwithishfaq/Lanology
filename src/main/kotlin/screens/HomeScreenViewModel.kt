package screens

import data.utils.ClientServerCommunicator
import data.utils.ServerBroadcaster
import data.utils.ServerFinderUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeScreenState(
    val msg: String = ""
)

class HomeScreenViewModel(
    private val serverFinderUtil: ServerFinderUtil = ServerFinderUtil(),
    private val messageSender: ClientServerCommunicator = ClientServerCommunicator()
) {
    val servers = serverFinderUtil.servers

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        onEvent(HomeScreenEvents.StartFindingServers)
    }

    fun onEvent(events: HomeScreenEvents) {
        when (events) {
            HomeScreenEvents.StartFindingServers -> {
                serverFinderUtil.startFinding()
            }

            is HomeScreenEvents.SendMessage -> {
                messageSender.sendMessage(events.ip, "Hi From Client")
            }
        }
    }

}