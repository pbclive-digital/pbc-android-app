package com.kavi.pbc.droid.news.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kavi.droid.color.palette.extension.shadow
import com.kavi.pbc.droid.data.dto.news.News
import com.kavi.pbc.droid.lib.common.ui.R
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily

@Composable
fun NewsItemForAdmin(
    modifier: Modifier = Modifier,
    isDraftNews: Boolean,
    news: News,
    onModify: () -> Unit,
    onPublish: (() -> Unit)? = null,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = news.title,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = PBCFontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row {
            val secondLineText = if (isDraftNews) {
                "created on ${news.getFormatCreatedDate()}"
            } else {
                "published on ${news.getFormatPublishedDate()}"
            }

            Text(
                modifier = Modifier.weight(1f),
                text = secondLineText,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = PBCFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_edit),
                    contentDescription = "Edit News",
                    tint = MaterialTheme.colorScheme.shadow,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .clickable {
                            onModify.invoke()
                        }
                )
                if (isDraftNews) {
                    Icon(
                        painter = painterResource(R.drawable.icon_publish),
                        contentDescription = "Publish News",
                        tint = MaterialTheme.colorScheme.shadow,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(4.dp)
                            .clickable {
                                onPublish?.invoke()
                            }
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.icon_delete),
                    contentDescription = "Delete News",
                    tint = MaterialTheme.colorScheme.shadow,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .clickable {
                            onDelete.invoke()
                        }
                )
            }
        }
    }
}