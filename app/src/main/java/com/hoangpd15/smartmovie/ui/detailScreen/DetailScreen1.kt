//package com.com.hoangpd15.smartmovie.ui.detailScreen
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.com.hoangpd15.smartmovie.ui.ui.theme.Movie_AssTheme
//import com.com.hoangpd15.smartmovie.viewModel.MovieDetailViewModel
//
//class DetailScreen : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            Movie_AssTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    Scaffold(
//                        topBar = {
//                            TopBar()
//                        }
//                    ) { paddingValues ->
//                        MovieDetailScreen(
//                            modifier = Modifier.padding(paddingValues),
//                            movieDetailViewModel = MovieDetailViewModel()
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun MovieDetailScreen(
//    modifier: Modifier = Modifier,
//    movieDetailViewModel: MovieDetailViewModel
//) {
//    var viewAll by remember { mutableStateOf(false) }
//    val listState = rememberLazyListState()
//    var previousScrollOffset by remember { mutableStateOf(0) }
//    val isLoading by movieDetailViewModel.isLoading.observeAsState(true)
//
//    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
//        if (listState.firstVisibleItemScrollOffset > previousScrollOffset) {
//            viewAll = false
//        }
//        previousScrollOffset = listState.firstVisibleItemScrollOffset
//    }
//    if (isLoading) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator()
//        }
//    }
//        Column(
//            modifier = modifier,
//        ) {
//            MovieInfoSectionView(movieDetailViewModel, viewAll) { viewAll = !viewAll }
//            LazyColumn(state = listState) {
//                item {
//                    MovieCastListSectionView(movieDetailViewModel)
//                }
//                item {
//                    SimilarMoviesSectionView()
//                }
//            }
//        }
//    }
//
//
//@Preview(showBackground = true)
//@Composable
//fun DetailScreenPreview() {
//    Movie_AssTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            Scaffold(
//            ) { paddingValues ->
//                MovieDetailScreen(
//                    modifier = Modifier.padding(paddingValues),
//                    movieDetailViewModel = MovieDetailViewModel()
//                )
//            }
//        }
//    }
//}