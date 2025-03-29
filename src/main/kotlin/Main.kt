import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import screens.home.HomeScreen
import screens.home.HomeScreenEvents
import screens.home.HomeScreenViewModel
import screens.sign_in.SigninScreen

@Composable
@Preview
fun App(viewModel: HomeScreenViewModel) {
    MaterialTheme {
        Column {
            val state by viewModel.state.collectAsState()
            if (state.showSignInScreen) {
                var userName by remember { mutableStateOf("") }
                SigninScreen(userName, onNameChange = { userName = it }, onSignInClick = {
                    if (userName.isNotBlank()) {
                        viewModel.setUserName(userName)
                        viewModel.onEvent(HomeScreenEvents.ShowSignIn)
                    }
                })
            } else {
                HomeScreen(viewModel)
            }
        }
    }
}

fun main() = application {
    val viewModel = HomeScreenViewModel()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Lanology",
    ) {
        App(viewModel = viewModel)
    }
}
