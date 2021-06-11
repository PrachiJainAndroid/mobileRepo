package com.prachi.airqualityindexcheck.repository.remote.network

import com.prachi.chatmessenger.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onCompletion
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.net.ssl.SSLSocketFactory

class AqiSocket : WebSocketClient(URI(Constants.WEB_SOCKET_URL)) {

    @ExperimentalCoroutinesApi
    private val publisherChannel = BroadcastChannel<String>(1)

    init {
        val socketFactory: SSLSocketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
        setSocketFactory(socketFactory)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun start(): Flow<String> {
        connect()
        return publisherChannel.asFlow().onCompletion { close() }
    }

    override fun onOpen(handshakedata: ServerHandshake?) {
    }

    @ExperimentalCoroutinesApi
    override fun onMessage(message: String?) {
        message?.also { publisherChannel.offer(it) }
    }

    @ExperimentalCoroutinesApi
    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        publisherChannel.close()
    }

    @ExperimentalCoroutinesApi
    override fun onError(ex: Exception?) {
        publisherChannel.close(ex)
    }
}