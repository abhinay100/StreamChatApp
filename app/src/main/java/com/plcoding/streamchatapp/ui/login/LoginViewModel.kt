package com.plcoding.streamchatapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.streamchatapp.utils.Constants.MIN_USERNAME_LENGTH
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Abhinay on 16/01/25.
 *
 *
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val client: ChatClient

): ViewModel() {

    private val _loginEvent = MutableSharedFlow<LogInEvent>()
    val loginEvent = _loginEvent.asSharedFlow()

    private fun isValidUsername(username: String) =
        username.length >= MIN_USERNAME_LENGTH

    fun connectUser(username: String) {
        val trimmedUsername = username.trim()
        viewModelScope.launch {
             if(isValidUsername(trimmedUsername)){
                 val result = client.connectGuestUser(
                     userId = trimmedUsername,
                     username = trimmedUsername

                 ).await()
                 if(result.isError) {
                     _loginEvent.emit(LogInEvent.ErrorLogin(result.error().message ?: "Unknown error"))
                     return@launch
                 }
                 _loginEvent.emit(LogInEvent.Success)
             } else {
                 _loginEvent.emit(LogInEvent.ErrorInputTooShort)
             }
        }

    }

    sealed class LogInEvent {
        object ErrorInputTooShort : LogInEvent()
        data class ErrorLogin(val error: String) : LogInEvent()
        object Success : LogInEvent()

    }

}