import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@Composable
fun app() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)),
        ) {
            Box {
                val stateVertical = rememberScrollState(0)


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(stateVertical)

                        .padding(end = 12.dp, bottom = 12.dp)
                ) {
                    Header()
                    title()
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 64.dp)
                    ) {
                        vehicleFormScreen()
                        Spacer(modifier = Modifier.height(16.dp))
                        VehicleSearchScreen()
                    }
                }

                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(stateVertical)
                )

            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Kotlin",
        state = rememberWindowState(size = DpSize(1100.dp, 1400.dp))
    ) {
        app()
    }
}
