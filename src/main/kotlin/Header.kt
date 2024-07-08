import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter
import kotlinx.coroutines.Dispatchers.Main

@Composable
fun Header() {
    val icon1: Painter = loadSvgPainter(Main::class.java.getResourceAsStream("/notification.svg")!!, LocalDensity.current)
    val icon2: Painter = loadSvgPainter(Main::class.java.getResourceAsStream("/user.svg")!!, LocalDensity.current)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color(0xFFFFD000)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "User 4001778 (colombia)",
            modifier = Modifier.padding(start = 76.dp).align(alignment = Alignment.CenterVertically),
            fontSize = 14.sp,
            color = Color(0xFF1A1446)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Icon(
                painter = icon1,
                contentDescription = "Icon 1",
                modifier = Modifier.size(34.dp),
                tint = Color.Unspecified // Usado para evitar que Compose intente aplicar un color específico al ícono
            )
            Icon(
                painter = icon2,
                contentDescription = "Icon 2",
                modifier = Modifier.size(34.dp),
                tint = Color.Unspecified // Modificado para evitar que Compose aplique un color específico al ícono
            )
            Button(
                onClick = { /* Handle logout */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFD000)), // Ajustado el color de fondo
                modifier = Modifier.padding(end = 76.dp),
                border = BorderStroke(1.dp, Color(0xFF1A1446))
            ) {
                Text("Cerrar sesión", color = Color(0xFF1A1446))
            }
        }
    }
}
