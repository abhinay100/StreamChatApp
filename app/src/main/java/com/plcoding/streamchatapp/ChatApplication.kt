package com.plcoding.streamchatapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.livedata.ChatDomain
import javax.inject.Inject


/**
 * Created by Abhinay on 16/01/25.
 *
 *
 */
@HiltAndroidApp
class ChatApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        // Move ChatDomain initialization to after Hilt initialization
        // to ensure proper dependency injection
        ChatDomain.Builder(client, applicationContext).build()
    }

    companion object {
        @Inject
        lateinit var client: ChatClient
    }
}