package com.example.serverandclientapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Message(
    val message: String
)

object ClientMessage {
    var message by mutableStateOf((Message("")))
}