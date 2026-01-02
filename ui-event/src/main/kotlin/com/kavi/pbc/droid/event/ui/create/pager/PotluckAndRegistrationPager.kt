package com.kavi.pbc.droid.event.ui.create.pager

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.event.potluck.PotluckItem
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.ui.create.EventCreateViewModel
import com.kavi.pbc.droid.event.ui.create.dialog.PotluckItemCreateDialog
import com.kavi.pbc.droid.lib.common.ui.component.AppButtonWithIcon
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import javax.inject.Inject

class PotluckAndRegistrationPager @Inject constructor() {

    @Composable
    fun PotluckAndRegistrationUI(viewModel: EventCreateViewModel = hiltViewModel()) {

        var isRegistrationChecked by remember { mutableStateOf(viewModel.newEvent.value.registrationRequired) }
        val availableSeatCount = remember { mutableStateOf(TextFieldValue(
            viewModel.newEvent.value.openSeatCount?.toString() ?: run { "" })) }

        var isPotluckChecked by remember { mutableStateOf(viewModel.newEvent.value.potluckAvailable) }
        val showCreatePotluckItemDialog = remember { mutableStateOf(false) }

        val potluckItemList by viewModel.potluckItemList.collectAsState()

        Box (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 4.dp, end = 4.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {

                Text(
                    text = stringResource(R.string.label_event_registration),
                    fontFamily = PBCFontFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                HorizontalDivider(
                    modifier = Modifier.padding(2.dp),
                    thickness = 2.dp
                )

                Text(
                    text = stringResource(R.string.phrase_event_registration),
                    fontFamily = PBCFontFamily,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.label_registration_check),
                        fontFamily = PBCFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Checkbox(
                        checked = isRegistrationChecked,
                        onCheckedChange = { newCheckedState ->
                            isRegistrationChecked = newCheckedState
                            viewModel.updateRegistrationRequiredFlag(newCheckedState)
                        }
                    )
                }

                if (isRegistrationChecked) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.label_seat_count),
                            fontFamily = PBCFontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        AppOutlineTextField(
                            modifier = Modifier
                                .width(150.dp)
                                .padding(top = 8.dp),
                            headingText = stringResource(R.string.label_count).uppercase(),
                            contentText = availableSeatCount,
                            keyboardType = KeyboardType.Number,
                            onValueChange = { newValue ->
                                availableSeatCount.value = newValue
                                if (newValue.text.isNotEmpty())
                                    viewModel.updateSeatCount(newValue.text.toInt())
                            }
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.label_event_potluck),
                    fontFamily = PBCFontFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                )

                HorizontalDivider(
                    modifier = Modifier.padding(2.dp),
                    thickness = 2.dp
                )

                Text(
                    text = stringResource(R.string.phrase_event_potluck),
                    fontFamily = PBCFontFamily,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.label_potluck_check),
                        fontFamily = PBCFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Checkbox(
                        checked = isPotluckChecked,
                        onCheckedChange = { newCheckedState ->
                            isPotluckChecked = newCheckedState
                            viewModel.updatePotluckAvailabilityFlag(newCheckedState)
                        }
                    )
                }

                if (isPotluckChecked) {
                    AppButtonWithIcon (
                        modifier = Modifier.padding(top = 4.dp),
                        label = stringResource(R.string.label_add_potluck_item),
                        icon = painterResource(R.drawable.icon_plus)
                    ) {
                        showCreatePotluckItemDialog.value = true
                    }

                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .clip(shape = RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        potluckItemList.forEachIndexed { index, potluckItem ->
                            key (potluckItem.itemId) {
                                PotluckListItem(
                                    potluckItem = potluckItem,
                                    onDelete = {
                                        viewModel.removePotluckItem(potluckItem)
                                    }
                                )
                                if (index < potluckItemList.lastIndex) {
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp, end = 8.dp),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.shadow
                                    )
                                }
                            }
                        }
                    }
                }

                PotluckItemCreateDialog(
                    showDialog = showCreatePotluckItemDialog,
                    onCreate = { potluckItem ->
                        viewModel.addPotluckItem(potluckItem)
                        showCreatePotluckItemDialog.value = false
                    },
                    onCancel = {
                        showCreatePotluckItemDialog.value = false
                    }
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

    @Composable
    fun PotluckListItem(
        modifier: Modifier = Modifier,
        potluckItem: PotluckItem,
        onDelete: (potluckItem: PotluckItem) -> Unit
    ) {
        val context = LocalContext.current
        val dismissState = rememberSwipeToDismissBoxState(
            initialValue = SwipeToDismissBoxValue.Settled,
            confirmValueChange = {
                when(it) {
                    SwipeToDismissBoxValue.EndToStart -> {
                        onDelete(potluckItem)
                        Toast.makeText(context, context.getString(R.string.label_remove_potluck_item), Toast.LENGTH_SHORT).show()
                    }
                    SwipeToDismissBoxValue.StartToEnd -> return@rememberSwipeToDismissBoxState false
                    SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
                }
                return@rememberSwipeToDismissBoxState true
            },
            // positional threshold of 25%
            positionalThreshold = { it * .25f }
        )

        SwipeToDismissBox(
            state = dismissState,
            modifier = modifier,
            backgroundContent = {
                DismissBackground(dismissState)
            },
            enableDismissFromStartToEnd = false,
            content = {
                PotluckItemUI(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    potluckItem = potluckItem
                )
            })
    }

    @Composable
    fun PotluckItemUI(
        modifier: Modifier = Modifier,
        potluckItem: PotluckItem
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .weight(.85f),
                text = potluckItem.itemName,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = PBCFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                    .weight(.15f),
                text = "${potluckItem.itemCount}",
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = PBCFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.End
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DismissBackground(dismissState: SwipeToDismissBoxState) {
        val color = when (dismissState.dismissDirection) {
            SwipeToDismissBoxValue.EndToStart -> Color(0xFFFF1744)
            SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
            SwipeToDismissBoxValue.Settled -> Color.Transparent
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(12.dp, 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier)
            Icon(
                Icons.Default.Delete,
                contentDescription = "delete"
            )
        }
    }
}