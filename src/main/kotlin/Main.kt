import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import screens.HomeScreen
import screens.HomeScreenViewModel

@Composable
@Preview
fun App(viewModel: HomeScreenViewModel) {
    MaterialTheme {
        Column {
            Text(
                "Lanology Client",
                fontSize = 18.sp,
                color = Color.Black
            )
            HomeScreen(viewModel)
        }
    }
}

fun main() = application {
    val viewModel = HomeScreenViewModel()
    Window(
        onCloseRequest = ::exitApplication, title = "Lanology",
    ) {
        App(viewModel = viewModel)
//        Text("Hiiiiiiii")
    }
}
