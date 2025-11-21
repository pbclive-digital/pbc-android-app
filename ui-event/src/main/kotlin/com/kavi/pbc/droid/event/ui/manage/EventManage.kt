package com.kavi.pbc.droid.event.ui.manage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.data.model.EventMangeMode
import com.kavi.pbc.droid.event.data.repository.local.EventLocalRepository
import com.kavi.pbc.droid.event.ui.common.EventItemForAdmin
import com.kavi.pbc.droid.event.ui.manage.dialog.DeleteConfirmationDialog
import com.kavi.pbc.droid.event.ui.manage.dialog.PublishConfirmationDialog
import com.kavi.pbc.droid.lib.common.ui.component.AppButtonWithIcon
import com.kavi.pbc.droid.lib.common.ui.component.Title
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import javax.inject.Inject

class EventManage @Inject constructor(
    private val eventLocalDataSource: EventLocalRepository
) {

    @Composable
    fun EventManageUI(navController: NavController, viewModel: EventManageViewModel = hiltViewModel()) {

        val showDeleteConfirmationDialog = remember { mutableStateOf(false) }
        val eventMode = remember { mutableStateOf(EventMangeMode.UNSELECTED) }
        val deletingEventId = remember { mutableStateOf("") }

        val showPublishConfirmationDialog = remember { mutableStateOf(false) }
        val publishingEventId = remember { mutableStateOf("") }

        Box (
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
                    titleText = stringResource(R.string.label_manage_events),
                )

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 30.dp)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    Text(
                        text = stringResource(R.string.phrase_manage_events),
                        fontFamily = PBCFontFamily,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    AppButtonWithIcon(
                        modifier = Modifier.padding(top = 12.dp),
                        label = stringResource(R.string.label_create_event),
                        icon = painterResource(R.drawable.icon_new_event)
                    ) {
                        navController.navigate("event/event-create")
                    }

                    DraftEventBlock(navController = navController, viewModel = viewModel,
                        publishConfirmation = showPublishConfirmationDialog,
                        publishingId = publishingEventId,
                        deleteConfirmation = showDeleteConfirmationDialog,
                        deletingEventId = deletingEventId,
                        eventMode = eventMode
                    )

                    ActiveEventBlock(navController = navController, viewModel = viewModel,
                        deleteConfirmation = showDeleteConfirmationDialog,
                        deletingEventId = deletingEventId, eventMode)
                }
            }
        }

        DeleteConfirmationDialog(
            showDialog = showDeleteConfirmationDialog,
            onAgree = {
                showDeleteConfirmationDialog.value = false
                viewModel.deleteGivenEvent(eventId = deletingEventId.value, eventMode = eventMode.value)
                deletingEventId.value = ""
            },
            onDisagree = {
                showDeleteConfirmationDialog.value = false
                deletingEventId.value = ""
            }
        )

        PublishConfirmationDialog(
            showDialog = showPublishConfirmationDialog,
            onAgree = {
                showPublishConfirmationDialog.value = false
                viewModel.publishDraftEvent(eventId = publishingEventId.value)
                publishingEventId.value = ""
            },
            onDisagree = {
                showPublishConfirmationDialog.value = false
                publishingEventId.value = ""
            }
        )
    }

    @Composable
    fun DraftEventBlock(navController: NavController, viewModel: EventManageViewModel,
                        publishConfirmation: MutableState<Boolean>,
                        publishingId: MutableState<String>,
                        deleteConfirmation: MutableState<Boolean>,
                        deletingEventId: MutableState<String>,
                        eventMode: MutableState<EventMangeMode>) {

        val draftEventList by viewModel.draftEventList.collectAsState()

        LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
            // Run your code here on ON_RESUME
            viewModel.fetchDraftEvents(isForceFetch = true)
        }

        Text(
            text = stringResource(R.string.label_draft_events),
            fontFamily = PBCFontFamily,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        )

        Column {
            if (draftEventList.isNotEmpty()) {
                draftEventList.forEachIndexed { index, event ->
                    EventItemForAdmin(event = event, isDraftEvent = true, onModify = {
                        // Navigate to edit screen
                        val tempEventKey = eventLocalDataSource.setModifyingEvent(event = event)
                        navController.navigate("event/event-edit/$tempEventKey")
                    }, onPublish = {
                        publishConfirmation.value = true
                        publishingId.value = event.id!!
                    }, onDelete = {
                        deleteConfirmation.value = true
                        eventMode.value = EventMangeMode.DRAFT
                        deletingEventId.value = event.id!!
                    })
                    if (index < draftEventList.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.shadow
                        )
                    }
                }
            } else {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.label_event_no_draft_event),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }

    @Composable
    fun ActiveEventBlock(navController: NavController, viewModel: EventManageViewModel,
                         deleteConfirmation: MutableState<Boolean>,
                         deletingEventId: MutableState<String>,
                         eventMode: MutableState<EventMangeMode>) {
        val activeEventList by viewModel.activeEventList.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.fetchActiveEvents()
        }

        Text(
            text = stringResource(R.string.label_active_events),
            fontFamily = PBCFontFamily,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        )

        Column {
            if (activeEventList.isNotEmpty()) {
                activeEventList.forEachIndexed { index, event ->
                    EventItemForAdmin(event = event, isDraftEvent = false, onModify = {
                        // Navigate to edit screen
                        val tempEventKey = eventLocalDataSource.setModifyingEvent(event = event)
                        navController.navigate("event/event-edit/$tempEventKey")
                    }, onDelete = {
                        deleteConfirmation.value = true
                        eventMode.value = EventMangeMode.ACTIVE
                        deletingEventId.value = event.id!!
                    })
                    if (index < activeEventList.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.shadow
                        )
                    }
                }
            } else {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.label_event_no_active_event),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}