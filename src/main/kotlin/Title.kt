import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun title() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(start = 76.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "GESTIÓN BÁSCULA",
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1446)
            )
        }
        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre el texto y la línea
        Divider(
            color = Color(0x16000000),
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
