package com.example.serverandclientapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AppScreen(networkUtils: NetworkUtils) {
    var deviceIpAddress by remember { mutableStateOf<String?>(null) }
    var inputIpAddress by remember { mutableStateOf("") }
    var inputPort by remember { mutableStateOf("") }
    var buttonText by remember { mutableStateOf("Start Server") }
    var isServerRunning by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        deviceIpAddress = networkUtils.getCurrentWifiIpAddress()
    }

    LaunchedEffect(deviceIpAddress) {
        // Start the server coroutine when the component is first created
        launch {
            withContext(Dispatchers.IO) {
                startServer(deviceIpAddress.orEmpty())
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(50.dp))
        Text(text = "Local Ip Address : ${deviceIpAddress.orEmpty()} on Port 4001")

        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = {
            if (buttonText.contains("Start")) {
                buttonText = "Stop Server"

            } else {
                buttonText = "Start Server"
            }
        }
        ) {
            Text(text = buttonText)
        }

        AddSpace()
        CreateTextField(
            value = inputIpAddress,
            onTextChange = { inputIpAddress = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        AddSpace()
        CreateTextField(
            value = inputPort,
            onTextChange = { inputPort = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun CreateTextField(
    value: String,
    onTextChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions
) {
    TextField(
        value = value,
        onValueChange = onTextChange,
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun AddSpace() {
    Spacer(modifier = Modifier.padding(20.dp))
}
