package com.kavi.pbc.droid.broadcast.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavi.pbc.droid.broadcast.data.model.SendMessageStatus
import com.kavi.pbc.droid.broadcast.data.repository.remote.BroadcastRemoteRepository
import com.kavi.pbc.droid.data.dto.message.Message
import com.kavi.pbc.droid.network.model.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BroadcastCreateViewModel @Inject constructor(
    private val broadcastRemoteRepository: BroadcastRemoteRepository
): ViewModel() {

    private val _message = MutableStateFlow(Message("", ""))
    private val _sendMessageStatus = MutableStateFlow(SendMessageStatus.NONE)
    val sendMessageStatus: StateFlow<SendMessageStatus> = _sendMessageStatus

    fun updateTitle(title: String) {
        _message.value.title = title
    }

    fun updateMessage(message: String) {
        _message.value.message = message
    }

    fun broadcastMessage() {
        _sendMessageStatus.value = SendMessageStatus.PENDING
        viewModelScope.launch {
            when(broadcastRemoteRepository.sendBroadcastMessage(_message.value)) {
                is ResultWrapper.NetworkError, is ResultWrapper.HttpError, is ResultWrapper.UnAuthError -> {
                    _sendMessageStatus.value = SendMessageStatus.FAILURE
                }
                is ResultWrapper.Success -> {
                    _sendMessageStatus.value = SendMessageStatus.SUCCESS
                }
            }
        }
    }

    private fun cleanSendMessage() {
        _message.value = Message("", "")
    }
}