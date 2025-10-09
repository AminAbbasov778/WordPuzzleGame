package com.example.wordsapp.core.base

import com.google.gson.Gson
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

abstract class BaseSocketHandler(
    protected val socket: Socket,
    private val gson: Gson
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val eventFlows = mutableMapOf<String, MutableSharedFlow<Any>>()
    private val eventClasses = mutableMapOf<String, Class<out Any>>()

    @Volatile
    private var isConnected = false


    fun connect() {
        if (isConnected) return


        socket.io().reconnection(true)
        socket.connect()

        socket.on(Socket.EVENT_CONNECT) {
            isConnected = true
            setupListeners()


        }



        socket.on(Socket.EVENT_DISCONNECT) {
            isConnected = false
        }

        socket.on(Socket.EVENT_CONNECT_ERROR) { args ->
        }
    }

    fun isConnected(): Boolean {
        return isConnected
    }

    fun disconnect() {
        socket.off()
        socket.disconnect()
        isConnected = false
    }


    fun <T : Any> listen(event: String, baseClass: Class<T>): SharedFlow<T> {
        val flow = MutableSharedFlow<T>(replay = 0)
        eventFlows[event] = flow as MutableSharedFlow<Any>
        eventClasses[event] = baseClass
        return flow.asSharedFlow()
    }

    private fun <T : Any> handleEvent(event: String, args: Array<Any>, baseClass: Class<T>) {
        val flow = eventFlows[event] ?: return
        val jsonStr = args.getOrNull(0)?.toString() ?: return
        try {
            val parsed = gson.fromJson(jsonStr, baseClass)
            scope.launch { (flow as MutableSharedFlow<T>).emit(parsed) }
        } catch (e: Exception) {
        }
    }

    fun emit(event: String, payload: JSONObject) {
        try {
            socket.emit(event, payload)
        } catch (e: Exception) {
        }
    }

    private fun setupListeners() {
        eventFlows.forEach { (event, _) ->
            socket.off(event)
            val baseClass = eventClasses[event] ?: Any::class.java
            socket.on(event) { args -> handleEvent(event, args, baseClass) }
        }
    }
}
