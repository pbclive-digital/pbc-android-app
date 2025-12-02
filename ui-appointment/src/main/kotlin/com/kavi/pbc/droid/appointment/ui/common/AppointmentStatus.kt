package com.kavi.pbc.droid.appointment.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.color.ColorPackageType
import com.kavi.droid.color.palette.color.MatPackage
import com.kavi.pbc.droid.data.dto.appointment.AppointmentStatus
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily

@Composable
fun AppointmentStatusTag(appointmentStatus: AppointmentStatus) {

    var tagColor by remember { mutableStateOf(Color.Transparent) }

    tagColor = when(appointmentStatus) {
        AppointmentStatus.PENDING -> MatPackage.MatGold.copy(colorPackage = ColorPackageType.PK_700).color
        AppointmentStatus.ACCEPTED -> MatPackage.MatLGreen.copy(colorPackage = ColorPackageType.PK_700).color
        AppointmentStatus.OVERDUE -> MatPackage.MatRed.copy(colorPackage = ColorPackageType.PK_700).color
    }

    Card (
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(tagColor)
                .clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = appointmentStatus.name,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = PBCFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun Preview_AppointmentStatus() {
    AppointmentStatusTag(appointmentStatus = AppointmentStatus.ACCEPTED)
}