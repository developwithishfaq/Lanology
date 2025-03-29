package screens.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun SigninScreen(userName: String, onNameChange: (String) -> Unit,onSignInClick:()->Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = userName,
            onValueChange = onNameChange
        )
        Button(
            onClick = {
                onSignInClick.invoke()
            }
        ){
            Text("Sign IN")
        }

    }
}