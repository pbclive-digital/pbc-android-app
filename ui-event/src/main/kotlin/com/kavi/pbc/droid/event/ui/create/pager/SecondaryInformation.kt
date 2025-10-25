package com.kavi.pbc.droid.event.ui.create.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavi.pbc.droid.data.dto.event.PotluckItem
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.ui.create.EventCreateViewModel
import com.kavi.pbc.droid.event.ui.create.common.NavigatorPanel
import com.kavi.pbc.droid.lib.common.ui.component.AppButtonWithIcon
import com.kavi.pbc.droid.lib.common.ui.component.AppFilledButton
import com.kavi.pbc.droid.lib.common.ui.component.AppIconButton
import com.kavi.pbc.droid.lib.common.ui.component.AppOutlineTextField
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import javax.inject.Inject

class SecondaryInformation @Inject constructor() {

    @Composable
    fun SecondaryInformationUI(viewModel: EventCreateViewModel = hiltViewModel()) {

        var isRegistrationChecked by remember { mutableStateOf(false) }
        val availableSeatCount = remember { mutableStateOf(TextFieldValue("")) }

        var isPotluckChecked by remember { mutableStateOf(false) }

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
                            onValueChange = { newValue ->
                                availableSeatCount.value = newValue
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
                        }
                    )
                }

                if (isPotluckChecked) {

                    AppButtonWithIcon (
                        modifier = Modifier.padding(top = 4.dp),
                        label = stringResource(R.string.label_add_potluck_item),
                        icon = painterResource(R.drawable.icon_plus)
                    ) {

                    }

                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(top = 12.dp)
                            .clip(shape = RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        potluckItemList.forEach { potluckItem ->
                            PotluckItemUI(potluckItem = potluckItem)
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

    @Composable
    fun PotluckItemUI(
        modifier: Modifier = Modifier,
        potluckItem: PotluckItem
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(
                text = potluckItem.itemName,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = PBCFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${potluckItem.itemCount}",
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = PBCFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}