package com.mock_project.ui.detailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jicsoftwarestudio.movie_ass.R
import com.mock_project.model.Cast
import com.mock_project.util.extension.MyImageComponent
import com.mock_project.viewModel.MovieDetailViewModel

@Composable
fun MovieCastListSectionView(movieDetailViewModel: MovieDetailViewModel) {
    val castMovie by movieDetailViewModel.castMovie.observeAsState()

    Column(
        Modifier.padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(text = "Cast", color = Color.Blue)
        HorizontalDivider(
            modifier = Modifier
                .height(4.dp)
                .width(100.dp),
            thickness = 4.dp,
            color = Color.Blue
        )
        when{
            castMovie.isNullOrEmpty() -> {
                Text(text = "The cast list is empty")
            }
            castMovie?.size!! > 6 ->{
                HorizontalGridView(items = castMovie!!)
            }
            else -> {
                HorizontalView(items = castMovie!!)
            }
        }
    }
}

@Composable
fun HorizontalView(items: List<Cast>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { cast ->
            GridItem(cast = cast)
        }
    }
}

@Composable
fun HorizontalGridView(items: List<Cast>) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        items(items) { cast ->
            GridItem(cast = cast)
        }
    }
}

@Composable
fun GridItem(cast: Cast) {
    Card(
        modifier = Modifier
            .height(450.dp)
            .width(105.dp)
            .padding(8.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (cast.profilePath != null) {
                MyImageComponent(
                    url = "https://image.tmdb.org/t/p/w500/${cast.profilePath}",
                    contentDescription = "Image description",
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.actor),
                    contentDescription = "",
                    modifier = Modifier
                        .height(105.dp)
                        .width(105.dp)
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = cast.name,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.6f))
                        .fillMaxWidth()
                )
            }
        }
    }
}