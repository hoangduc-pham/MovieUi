package com.hoangpd15.smartmovie.ui.detailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoangpd15.smartmovie.R
import com.hoangpd15.smartmovie.util.extension.MyImageComponent
import com.hoangpd15.smartmovie.util.extension.formatDate

@Composable
fun MovieInfoSectionView(
    movieDetailViewModel: MovieDetailViewModel,
    viewAll: Boolean,
    onClick: () -> Unit
) {
    val movieDetail by movieDetailViewModel.movieDetail.observeAsState()
    val movieDetailGenres by movieDetailViewModel.movieDetailGenres.observeAsState()
    val language by movieDetailViewModel.language.observeAsState()

    Column(Modifier.padding(15.dp)) {
        Row {
            MyImageComponent(
                url = "https://image.tmdb.org/t/p/w500/${movieDetail?.posterPath}",
                contentDescription = "Image description"
            )
            Column(Modifier.padding(10.dp)) {
                Text(
                    text = "${movieDetail?.title}",
                    fontSize = 25.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                )
                movieDetailGenres?.let {
                    Text(
                        text = it.joinToString(" | ") { it.name },
                        fontSize = 15.sp
                    )
                }
                Row(verticalAlignment = Alignment.Bottom) {
                    val voteAverage = movieDetail?.voteAverage ?: 0.0
                    RatingBar(rating = voteAverage, maxRating = 5)
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = String.format("%.1f/10", movieDetail?.voteAverage),
                        fontSize = 15.sp
                    )
                }
                language?.let {
                    Text(
                        text = "Language: ${it.firstOrNull()?.englishName ?: "English"}",
                        fontSize = 15.sp
                    )
                }
                val formattedDate = formatDate(movieDetail?.releaseDate)
                Text(
                    text = "Ngày phát hành: $formattedDate",
                    fontSize = 15.sp
                )
                Text(
                    text = "Thời lượng: ${movieDetail?.runtime} phút",
                    fontSize = 15.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = if (movieDetail?.overview.isNullOrEmpty()) stringResource(id = R.string.unavailable_movie) else {
                "${movieDetail?.overview}"
            },
            fontSize = 15.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            fontFamily = FontFamily.SansSerif,
            maxLines = if (viewAll) Int.MAX_VALUE else 3,
            overflow = if (viewAll) TextOverflow.Visible else TextOverflow.Ellipsis
        )
        ClickableText(
            text = AnnotatedString(if (viewAll) "hide" else "view all"),
            onClick = {
                onClick()
            }, style = TextStyle(color = Color.Blue, fontSize = 17.sp)
        )
    }
}

@Composable
fun RatingBar(
    rating: Double,
    maxRating: Int = 5,
    activeColor: Color = Color(0xFFFFFACD3A),
    inactiveColor: Color = Color.Gray
) {
    Row {
        repeat(maxRating) { index ->
            val starColor = if (index < rating / 2) activeColor else inactiveColor
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
    }
}