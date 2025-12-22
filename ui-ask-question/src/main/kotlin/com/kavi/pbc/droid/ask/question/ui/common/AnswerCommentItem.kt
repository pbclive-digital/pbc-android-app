package com.kavi.pbc.droid.ask.question.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kavi.pbc.droid.data.dto.question.AnswerComment
import com.kavi.pbc.droid.data.dto.user.UserSummary
import com.kavi.pbc.droid.lib.common.ui.theme.PBCFontFamily

@Composable
fun AnswerCommentItem(modifier: Modifier = Modifier,
                      answerComment: AnswerComment) {

    Column(
        modifier = modifier
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Box (
                modifier = Modifier
                    .padding(top = 12.dp, start = 4.dp, end = 4.dp)
                    .size(60.dp)
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
                    model = answerComment.author.imageUrl,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(5.dp)
                        .clip(CircleShape)
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = 12.dp, start = 4.dp, end = 4.dp)
                    .weight(1f),
            ) {
                Text(
                    text = answerComment.author.name,
                    fontFamily = PBCFontFamily,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = answerComment.comment,
                    fontFamily = PBCFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, end = 4.dp)
                        .fillMaxWidth(),
                    text = "on ${answerComment.getFormatCreatedDate()}",
                    fontFamily = PBCFontFamily,
                    fontSize = 12.sp,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(
                top = 8.dp,
                start = 12.dp
            ),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Preview
@Composable
fun Preview_AnswerCommentItem() {
    AnswerCommentItem(
        answerComment = AnswerComment(
            comment = "Question Title for sample preview",
            createdTime = 0,
            author = UserSummary(
                name = "Firstname Lastname",
                imageUrl = ""
            )
        )
    )
}