package com.kavi.pbc.droid.lib.common.ui.component.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.user.User
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserViewBottomSheet(
    sheetState: SheetState,
    showSheet: MutableState<Boolean>,
    selectedUser: User
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            showSheet.value = false
        },
        containerColor = MaterialTheme.colorScheme.background,
        scrimColor = MaterialTheme.colorScheme.shadow.copy(alpha = .5f)
    ) {
        Column (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .border(
                            border = BorderStroke(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.tertiary
                            ),
                            shape = CircleShape
                        )
                ) {
                    AsyncImage(
                        model = selectedUser.profilePicUrl,
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(140.dp)
                            .padding(5.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Text(
                text = "${selectedUser.firstName} ${selectedUser.lastName}",
                fontFamily = PBCFontFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )

            BasicUserInfoCard(profileUser = selectedUser)
        }
    }
}