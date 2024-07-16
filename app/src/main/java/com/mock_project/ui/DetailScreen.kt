package com.mock_project.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jicsoftwarestudio.movie_ass.R
import com.mock_project.model.Cast
import com.mock_project.ui.ui.theme.Movie_AssTheme
import com.mock_project.viewModel.MovieDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DetailScreen : ComponentActivity() {
    val movieDetailViewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Movie_AssTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        topBar = {
                            TopBar()
                        }
                    ) { paddingValues ->
                        MovieDetailScreen(modifier = Modifier.padding(paddingValues),
                            movieDetailViewModel = MovieDetailViewModel())
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Detail Movie")
        }
    )
}
@Composable
fun SimilarMoviesSectionView() {
    Column(
        Modifier
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(text = "Similar movies", color = Color.Blue)
        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .height(4.dp)
                .width(100.dp),
            thickness = 4.dp,
            color = Color.Blue
        )
        Card(
            modifier = Modifier
                .height(175.dp)
                .fillMaxWidth(),
        ) {
            Column(
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mv3),
                    contentDescription = "",
                    modifier = Modifier
                        .height(101.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(
                        Modifier.padding(10.dp)
                    ) {
                        Text(text = "Miclrale from haeven", fontSize = 15.sp)
                        Text(text = "Drama | Fantasy | Romance", fontSize = 15.sp)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .height(175.dp)
                .fillMaxWidth(),
        ) {
            Column(
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mv3),
                    contentDescription = "",
                    modifier = Modifier
                        .height(101.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(
                        Modifier.padding(10.dp)
                    ) {
                        Text(text = "Miclrale from haeven", fontSize = 15.sp)
                        Text(text = "Drama | Fantasy | Romance", fontSize = 15.sp)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .height(175.dp)
                .fillMaxWidth(),
        ) {
            Column(
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mv3),
                    contentDescription = "",
                    modifier = Modifier
                        .height(101.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(
                        Modifier.padding(10.dp)
                    ) {
                        Text(text = "Miclrale from haeven", fontSize = 15.sp)
                        Text(text = "Drama | Fantasy | Romance", fontSize = 15.sp)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_star_24),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun MovieDetailScreen(modifier: Modifier = Modifier,
                      movieDetailViewModel: MovieDetailViewModel) {
    var viewAll by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var previousScrollOffset by remember { mutableStateOf(0) }
    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        if (listState.firstVisibleItemScrollOffset > previousScrollOffset) {
            viewAll = false
        }
        previousScrollOffset = listState.firstVisibleItemScrollOffset
    }
    Column(
        modifier = modifier,
    ) {
        MovieInfoSectionView(movieDetailViewModel,viewAll){ viewAll = !viewAll}
        LazyColumn(state = listState) {
            item {
                MovieCastListSectionView(movieDetailViewModel)
            }
            item {
                SimilarMoviesSectionView()
            }
        }
    }
}
@Composable
fun HorizontalGridView(items: List<Cast>) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)) {
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
            MyImageComponent(
                url = "https://image.tmdb.org/t/p/w500/${cast.profilePath}",
                contentDescription = "Image description",
            )
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
                )
            }
        }
    }
}


@Composable
fun MovieCastListSectionView(movieDetailViewModel: MovieDetailViewModel) {
    val castMovie by movieDetailViewModel.castMovie.observeAsState()

    Column(
        Modifier.padding(15.dp)
    ) {
        Text(text = "Cast", color = Color.Blue)
        HorizontalDivider(
            modifier = Modifier
                .height(4.dp)
                .width(100.dp),
            thickness = 4.dp,
            color = Color.Blue
        )
        castMovie?.let { castList ->
            HorizontalGridView(items = castList)
        }
    }
}

@Composable
fun MyImageComponent(url: String, contentDescription: String) {
    val painter = rememberImagePainter(
        data = url
    )

    androidx.compose.foundation.Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier
            .height(200.dp)
            .width(140.dp),
        contentScale = ContentScale.Crop
    )
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
@Composable
fun formatDate(dateString: String?): String {
    return dateString?.let {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = inputFormat.parse(it)
            outputFormat.format(date)
        } catch (e: Exception) {
            "Invalid date"
        }
    } ?: "Date is null"
}
@Composable
fun MovieInfoSectionView(movieDetailViewModel: MovieDetailViewModel, viewAll: Boolean, onClick: () -> Unit) {
    val movieDetail by movieDetailViewModel.movieDetail.observeAsState()
    val movieDetailGenres by movieDetailViewModel.movieDetailGenres.observeAsState()
    val language by movieDetailViewModel.language.observeAsState()
    val movieId = (LocalContext.current as? DetailScreen)?.intent?.getIntExtra("id", -1) ?: -1
    movieDetailViewModel.fetchMovieDetail(movieId)

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
                    Text(text = String.format("%.1f/10", movieDetail?.voteAverage),
                        fontSize = 15.sp)
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
            text = "${movieDetail?.overview}",
            fontSize = 15.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            fontFamily = FontFamily.SansSerif,
            maxLines =  if(viewAll) Int.MAX_VALUE else 3,
            overflow = if(viewAll) TextOverflow.Visible else TextOverflow.Ellipsis
        )
        ClickableText(
            text = AnnotatedString( if(viewAll) "hide" else "view all"),
            onClick = { onClick()
            }, style = TextStyle(color = Color.Blue, fontSize = 17.sp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    Movie_AssTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
            ) { paddingValues ->
                MovieDetailScreen(modifier = Modifier.padding(paddingValues),
                    movieDetailViewModel = MovieDetailViewModel())
            }
        }
    }
}