package com.kavi.pbc.droid.broadcast.ui.create

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavi.pbc.droid.broadcast.R
import com.kavi.pbc.droid.broadcast.data.model.SendMessageStatus
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppFullScreenLoader
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineMultiLineTextField
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.component.Title
import javax.inject.Inject

class BroadcastCreate @Inject constructor() {

    @Composable
    fun BroadcastCreateUI(viewModel: BroadcastCreateViewModel = hiltViewModel()) {

        val messageTitle = remember { mutableStateOf(TextFieldValue()) }
        val messageContent = remember { mutableStateOf(TextFieldValue()) }

        val sendMessageStatus by viewModel.sendMessageStatus.collectAsState()

        val context = LocalContext.current

        Box {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Title(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        titleText = stringResource(R.string.label_broadcast_message),
                    )

                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp, top = 20.dp, bottom = 30.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        AppOutlineTextField (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            headingText = stringResource(R.string.label_broadcast_message_title),
                            contentText = messageTitle,
                            onValueChange = { newValue ->
                                messageTitle.value = newValue
                                viewModel.updateTitle(messageTitle.value.text)
                            }
                        )

                        AppOutlineMultiLineTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(top = 8.dp),
                            maxLines = 20,
                            headingText = stringResource(R.string.label_broadcast_message_content),
                            contentText = messageContent,
                            onValueChange = { newValue ->
                                messageContent.value = newValue
                                viewModel.updateMessage(messageContent.value.text)
                            }
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        AppFilledButton(
                            label = stringResource(R.string.label_broadcast_message_send)
                        ) {
                            viewModel.broadcastMessage()
                        }
                    }
                }
            }

            when(sendMessageStatus) {
                SendMessageStatus.NONE -> { /*Nothing to implement*/ }
                SendMessageStatus.PENDING -> {
                    AppFullScreenLoader()
                }
                SendMessageStatus.FAILURE -> {
                    Toast.makeText(context, stringResource(R.string.label_broadcast_message_send_failed), Toast.LENGTH_LONG).show()
                }
                SendMessageStatus.SUCCESS -> {
                    messageTitle.value = TextFieldValue()
                    messageContent.value = TextFieldValue()
                    Toast.makeText(context, stringResource(R.string.label_broadcast_message_send_success), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}