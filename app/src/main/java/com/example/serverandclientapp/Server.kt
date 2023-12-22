package com.example.serverandclientapp

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket

fun startServer(ipAddress: String) {
    try {
        println("Server Start")
        val serverSocket = ServerSocket()
        serverSocket.bind(InetSocketAddress(ipAddress, 4001))
        println("Server is listening to :: ${serverSocket.localSocketAddress}")

        while (true) { // Add a condition to exit the loop

            val clientSocket = serverSocket.accept()
            println("Accept ${clientSocket.remoteSocketAddress}")
            val clientHandlerThread = Thread {
                handleClient(clientSocket)
            }
            println("Process :: Start Client Handler")
            clientHandlerThread.start()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun handleClient(clientSocket: Socket) {
    try {

        val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        /*val writer = PrintWriter(clientSocket.getOutputStream(), true)*/

        println("Process :: Get Client Message")

        val bufferSize = 1024 // Choose an appropriate buffer size
        val charBuffer = CharArray(bufferSize)
        val bytesRead = reader.read(charBuffer, 0, bufferSize)
        if (bytesRead > 0) {
            val clientMessage = String(charBuffer, 0, bytesRead)
            println("Received from client: $clientMessage")
            ClientMessage.message = Message(clientMessage)
        } else {
            println("No data received from client.")
        }


        /*writer.println("Server received: $clientMessage")*/
        println("Process :: Client disconnected")
        reader.close()

    } catch (e: Exception) {
        println("Error")
        println(e)
    } finally {
        clientSocket.close()
        println("Process :: Client Socket Close")
        printLine()
    }

}

fun printLine() {
    repeat(30) {
        print("-")
    }
    println()
}

fun startClient(ipAddress: String, port: String, message: String) {
    println()
    if (ipAddress.isNotEmpty()) {
        try {// Connect to the server
            val socket = Socket(ipAddress, port.toInt())
            //val reader = BufferedReader(InputStreamReader(socket.getInputStream(), "UTF-8"))
            val writer = PrintWriter(OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true)

            // Read welcome message from the server

            // Send messages to the server
            writer.println(message)

            // Close the client socket
            writer.close()
            socket.close()
        } catch (e: Exception) {
            println("Exception in startClient: $e")
        }
    }
}

