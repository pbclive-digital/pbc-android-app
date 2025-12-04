package com.kavi.pbc.droid.event.ui.common

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kavi.droid.color.palette.extension.quaternary
import com.kavi.pbc.droid.data.dto.event.potluck.EventPotluckItem
import com.kavi.pbc.droid.event.R
import com.kavi.pbc.droid.event.ui.selected.EventSelectedViewModel
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily
import java.util.Locale

@Composable
fun EventPotluckItemUI(modifier: Modifier = Modifier,
                       viewModel: EventSelectedViewModel,
                       potluckItem: EventPotluckItem,
                       currentUserContributions: Int) {

    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Row (
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
            .clip( RoundedCornerShape(8.dp))
            .shadow(elevation = 2.dp)
            .clickable {
                isLoading = true
                viewModel.signUpForPotluckItem(potluckItem) {
                    isLoading = false
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                        .weight(.8f),
                    text = potluckItem.itemName,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = PBCFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier.weight(.2f),
                    contentAlignment = Alignment.Center
                ) {
                    if (!isLoading) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            if (potluckItem.availableCount > potluckItem.contributorList.size) {
                                Image(
                                    painter = painterResource(R.drawable.icon_add_item),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .clickable {
                                            isLoading = true
                                            viewModel.signUpForPotluckItem(potluckItem) { isSuccess ->
                                                isLoading = false
                                                if (!isSuccess)
                                                    Toast.makeText(context, context.getString(R.string.phrase_event_potluck_reg_failure),
                                                        Toast.LENGTH_LONG)
                                            }
                                        }
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            if (potluckItem.contributorList.isNotEmpty()) {
                                Image(
                                    painter = painterResource(R.drawable.icon_remove_item),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(35.dp)
                                        .clickable {
                                            isLoading = true
                                            viewModel.signOutFromPotluckItem(potluckItem) { isSuccess ->
                                                isLoading = false
                                                if (!isSuccess)
                                                    Toast.makeText(context, context.getString(R.string.phrase_event_potluck_unreg_failure),
                                                        Toast.LENGTH_LONG)
                                            }
                                        }
                                )
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .width(35.dp)
                                .height(35.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

            Row (
                modifier = Modifier
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = { potluckItem.completionProgress() },
                    color = MaterialTheme.colorScheme.quaternary,
                    trackColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .weight(.85f)
                        .padding(start = 4.dp, end = 8.dp)
                )

                Text(
                    modifier = Modifier
                        .weight(.15f),
                    text = "${potluckItem.contributorList.size}/${potluckItem.availableCount}",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = PBCFontFamily,
                    textAlign = TextAlign.End,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (currentUserContributions != 0) {
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                        .fillMaxWidth(),
                    text = String.format(Locale.US,
                        stringResource(R.string.label_event_user_contribution_count),
                        currentUserContributions, potluckItem.itemName),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = PBCFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview_EventPotluckItemUI() {
    EventPotluckItemUI(
        viewModel = hiltViewModel(),
        potluckItem = EventPotluckItem(
            itemId = "",
            itemName = "Item One",
            availableCount = 5,
            contributorList = mutableListOf()
        ),
        currentUserContributions = 0
    )
}