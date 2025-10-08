package com.example.wordsapp.core.di

import com.example.wordsapp.core.network.SocketHandler
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent :: class)
object SocketModule {


    @Provides
    @Singleton
    fun provideSocket(): io.socket.client.Socket {
        return IO.socket("http://114.29.236.110:3000",  IO.Options.builder().apply {
            setTransports(arrayOf("websocket"))
            setUpgrade(true)
            setRememberUpgrade(true)
            setForceNew(true)
            setReconnection(true)
        }.build())
    }

    @Provides
    @Singleton
    fun provideSocketHandler(socket : io.socket.client.Socket,gson: Gson): SocketHandler {
        return SocketHandler(socket, gson)

    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }


}