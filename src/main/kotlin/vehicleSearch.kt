import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazecast.jSerialComm.SerialPort
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun VehicleSearchScreen() {
    var plateSearch by remember { mutableStateOf("") }
    var vehicleDataList by remember { mutableStateOf<List<VehicleData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showResults by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        when (showResults) {
            false -> SearchView(
                plateSearch = plateSearch,
                onPlateSearchChange = { plateSearch = it },
                onSearchClick = {
                    isLoading = true
                    errorMessage = null
                    fetchVehicleData(plateSearch) { result ->
                        isLoading = false
                        result.onSuccess { data ->
                            vehicleDataList = listOf(data)
                            showResults = true
                        }.onFailure { error ->
                            errorMessage = error.message
                        }
                    }
                },
                isLoading = isLoading,
                errorMessage = errorMessage
            )
            true -> ResultView(
                vehicleDataList = vehicleDataList,
                onBackClick = { showResults = false }
            )
        }
    }
}

@Composable
fun SearchView(
    plateSearch: String,
    onPlateSearchChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    isLoading: Boolean,
    errorMessage: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .border(2.dp, Color(0x18000000))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Consulta vehículo",
                style = MaterialTheme.typography.h6.copy(fontSize = 18.sp),
                color = Color(0xFF1A1446),
                modifier = Modifier.padding(end = 16.dp)
            )
            Spacer(modifier = Modifier.width(66.dp))
            OutlinedTextField(
                value = plateSearch,
                onValueChange = onPlateSearchChange,
                label = { Text("Ingrese placa") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF00509E),
                cursorColor = Color(0xFF00509E),
                    focusedLabelColor = Color.Black),
                modifier = Modifier
                    .size(width = 300.dp, height = 56.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onSearchClick,
                modifier = Modifier
                    .size(width = 100.dp, height = 56.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF99E5EA))
            ) {
                Text("Buscar", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(color = Color(0xFF99E5EA))
        }

        errorMessage?.let {
            Text(text = it, color = Color.Red)
        }
    }
}

@Composable
fun ResultView(vehicleDataList: List<VehicleData>, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF99E5EA)),
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("Atrás", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Información de consulta",
                style = MaterialTheme.typography.h5.copy(fontSize = 14.sp),
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Mostrando ${vehicleDataList.size} de ${vehicleDataList.size} registros",
                style = MaterialTheme.typography.h6.copy(fontSize = 14.sp),
                color = Color.Black,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        VehicleDataTable(vehicleDataList)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Concatenate the vehicle data into a single string
                val dataToPrint = vehicleDataList.joinToString(separator = "\n") { vehicleData ->
                    "${vehicleData.plate}, ${vehicleData.model}, ${vehicleData.brand}, ${vehicleData.color}, ${vehicleData.weight}, ${vehicleData.date}, ${vehicleData.hour}, ${vehicleData.pricing}"
                }
                // Call the function to send data to the serial port
                testSerialPortCommunication("COM3", "COM4", dataToPrint)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF99E5EA)),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .shadow(4.dp)
        ) {
            Text("Imprimir", color = Black)
        }
    }
}


@Composable
fun VehicleDataTable(vehicleDataList: List<VehicleData>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0F7FA))
                .padding(8.dp)
        ) {
            Text(
                text = "Placa",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Modelo",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Marca",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Color",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Peso",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Fecha",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Hora",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Valor",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        vehicleDataList.forEach { vehicleData ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = vehicleData.plate, modifier = Modifier.weight(1f))
                Text(text = vehicleData.model, modifier = Modifier.weight(1f))
                Text(text = vehicleData.brand, modifier = Modifier.weight(1f))
                Text(text = vehicleData.color, modifier = Modifier.weight(1f))
                Text(text = vehicleData.weight, modifier = Modifier.weight(1f))
                Text(text = vehicleData.date, modifier = Modifier.weight(1f))
                Text(text = vehicleData.hour, modifier = Modifier.weight(1f))
                Text(text = vehicleData.pricing, modifier = Modifier.weight(1f))
            }
        }
    }
}

fun fetchVehicleData(plateId: String, callback: (Result<VehicleData>) -> Unit) {
    val call = RetrofitClient.instance.getVehicleData(plateId)
    call.enqueue(object : Callback<VehicleData> {
        override fun onResponse(call: Call<VehicleData>, response: Response<VehicleData>) {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    callback(Result.success(data))
                } ?: run {
                    callback(Result.failure(Exception("No data found")))
                }
            } else {
                callback(Result.failure(Exception("Error: ${response.code()}")))
            }
        }

        override fun onFailure(call: Call<VehicleData>, t: Throwable) {
            callback(Result.failure(t))
        }
    })
}



fun testSerialPortCommunication(writePortName: String, readPortName: String, data: String) {
    val writePort = SerialPort.getCommPort(writePortName)
    val readPort = SerialPort.getCommPort(readPortName)

    writePort.baudRate = 9600
    readPort.baudRate = 9600

    if (!writePort.openPort()) {
        println("Error: Cannot open write port $writePortName")
        return
    }

    if (!readPort.openPort()) {
        println("Error: Cannot open read port $readPortName")
        writePort.closePort()
        return
    }

    // Coroutine to read data
    GlobalScope.launch {
        val readBuffer = ByteArray(1024)
        while (readPort.isOpen) {
            val numRead = readPort.readBytes(readBuffer, readBuffer.size)
            if (numRead > 0) {
                val receivedData = String(readBuffer, 0, numRead)
                println("Received: $receivedData")
            }
        }
    }

    writePort.outputStream.write(data.toByteArray())
    writePort.outputStream.flush()
    println("Data sent: $data")

    // Wait for a bit to ensure data is read
    Thread.sleep(1000)

    // Close ports
    writePort.closePort()
    readPort.closePort()
}

