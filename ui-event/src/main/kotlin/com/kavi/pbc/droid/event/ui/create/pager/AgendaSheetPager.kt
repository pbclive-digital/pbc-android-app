package com.kavi.pbc.droid.event.ui.create.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.ui.create.EventCreateViewModel
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import javax.inject.Inject

class AgendaSheetPager @Inject constructor() {

    @Composable
    fun AgendaSheetPagerUI(viewModel: EventCreateViewModel = hiltViewModel()) {

        var isAgendaChecked by remember { mutableStateOf(viewModel.newEvent.value.agendaAvailable) }

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
                    text = stringResource(R.string.label_event_agenda_in_admin),
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
                    text = stringResource(R.string.phrase_event_agenda_in_admin),
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
                        text = stringResource(R.string.label_event_agenda_available),
                        fontFamily = PBCFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Checkbox(
                        checked = isAgendaChecked,
                        onCheckedChange = { newCheckedState ->
                            isAgendaChecked = newCheckedState
                            viewModel.updateAgendaFlag(newCheckedState)
                        }
                    )
                }

                if (isAgendaChecked) {
                    
                }
            }
        }
    }
}