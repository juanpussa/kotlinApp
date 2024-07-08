import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.awt.Color.black

@Composable
fun vehicleFormScreen() {
    var plate by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0FBFC))
            .border(BorderStroke(1.1.dp, Color(0x2200509E)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0FBFC))
                .padding(start = 2.dp)
                .border(BorderStroke(1.1.dp, Color(0x2200509E)))
                .padding(36.dp)
        ) {
            Text(
                text = "Datos del Vehiculo",
                style = MaterialTheme.typography.h6.copy(fontSize = 18.sp),
                color = Color(0xFF1A1446)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = plate,
                    onValueChange = { plate = it },
                    label = { Text("Placa") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Color(0xFF00509E),
                        cursorColor = Color(0xFF00509E),
                        focusedLabelColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = model,
                    onValueChange = { model = it },
                    label = { Text("Modelo") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Color(0xFF00509E),
                        cursorColor = Color(0xFF00509E),
                        focusedLabelColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = brand,
                    onValueChange = { brand = it },
                    label = { Text("Marca") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Color(0xFF00509E),
                        cursorColor = Color(0xFF00509E),
                        focusedLabelColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = color,
                    onValueChange = { color = it },
                    label = { Text("Color") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Color(0xFF00509E),
                        cursorColor = Color(0xFF00509E),
                        focusedLabelColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Peso") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Color(0xFF00509E),
                        cursorColor = Color(0xFF00509E),
                        focusedLabelColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("dd/mm/aaaa") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Color(0xFF00509E),
                        cursorColor = Color(0xFF00509E),
                        focusedLabelColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Hora") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Color(0xFF00509E),
                        cursorColor = Color(0xFF00509E),
                        focusedLabelColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = value,
                    onValueChange = { value = it },
                    label = { Text("Valor") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Color(0xFF00509E),
                        cursorColor = Color(0xFF00509E),
                        focusedLabelColor = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = {
                        plate = ""
                        model = ""
                        brand = ""
                        color = ""
                        weight = ""
                        date = ""
                        time = ""
                        value = ""
                    },
                    modifier = Modifier.size(width = 150.dp, height = 48.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFF00509E))
                ) {
                    Text("Limpiar", color = Color(0xFF000000))
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        println("Enviando datos...")
                        scope.launch {
                            try {
                                val vehicleData = VehicleData(
                                    plate = plate,
                                    model = model,
                                    brand = brand,
                                    color = color,
                                    weight = weight,
                                    date = date,
                                    hour = time,
                                    pricing = value
                                )
                                RetrofitClient.instance.postVehicleData(plate, vehicleData)
                                    .enqueue(object : Callback<Void> {
                                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                            if (response.isSuccessful) {
                                                println("Datos enviados exitosamente")
                                            } else {
                                                println("Error al enviar datos: ${response.message()}")
                                            }
                                        }

                                        override fun onFailure(call: Call<Void>, t: Throwable) {
                                            println("Error: ${t.message}")
                                        }
                                    })
                            } catch (e: Exception) {
                                println("Error: ${e.message}")
                            }
                        }
                    },
                    modifier = Modifier.size(width = 150.dp, height = 48.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF99E5EA), contentColor = Color.Black)
                ) {
                    Text("Pesar")
                }
            }
        }
    }
}
