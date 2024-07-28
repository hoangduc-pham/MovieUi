//// MoviesViewModelTest.kt
//package com.hoangpd15.smartmovie
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import com.hoangpd15.smartmovie.model.dataRemote.MovieNowPlayingApi
//import com.hoangpd15.smartmovie.model.dataRemote.MoviePopularApi
//import com.hoangpd15.smartmovie.model.dataRemote.MovieTopRateApi
//import com.hoangpd15.smartmovie.model.dataRemote.MovieUpComingApi
//import com.hoangpd15.smartmovie.model.Movie
//import com.hoangpd15.smartmovie.model.MovieResponse
//import com.hoangpd15.smartmovie.ui.homeScreen.movieScreen.MoviesViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.setMain
//import org.junit.*
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.Mockito.*
//import org.mockito.junit.MockitoJUnitRunner
//
//@RunWith(MockitoJUnitRunner::class)
//class MoviesViewModelTest {
//
//    @get:Rule var rule = InstantTaskExecutorRule()
//
//    @Mock
//    private lateinit var observer: Observer<List<Movie>>
//
//    @Mock
//    private lateinit var apiMoviePopular: MoviePopularApi
//    @Mock
//    private lateinit var apiMovieTopRated: MovieTopRateApi
//    @Mock
//    private lateinit var apiMovieUpComing: MovieUpComingApi
//    @Mock
//    private lateinit var apiMovieNowPlaying: MovieNowPlayingApi
//
//
//    private lateinit var viewModel: MoviesViewModel
//
//    @Before
//    fun setUp() {
//        Dispatchers.setMain(Dispatchers.Unconfined)
//        viewModel = MoviesViewModel()
////        viewModel.popularMoviesApi = apiMoviePopular
////        viewModel.topRateMoviesApi = apiMovieTopRated
////        viewModel.upComingMoviesApi = apiMovieUpComing
////        viewModel.nowPlayingMoviesApi = apiMovieNowPlaying
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun testPopularMovies() = runBlocking {
//        val movieList = listOf(Movie(1, "Movie 1", "poster_path", 100,"backdrop_path","",8.5))
//        `when`(apiMoviePopular.getPopularMovies(1)).thenReturn(MovieResponse(movieList))
//
//        viewModel.popularMovies.observeForever(observer)
////        verify(observer).onChanged(movieList)
//        viewModel.popularMovies.removeObserver(observer)
//    }
//
//    @Test
//    fun testTopRatedMovies() = runBlocking {
//        val movieList = listOf(Movie(1, "Movie 1", "poster_path", 100,"backdrop_path","",8.5))
//        `when`(apiMovieTopRated.getTopRateMovies(1)).thenReturn(MovieResponse(movieList))
//
//        viewModel.topRatedMovies.observeForever(observer)
//        viewModel.topRatedMovies.removeObserver(observer)
//    }
//
//    @Test
//    fun testNowPlayingMoive() = runBlocking {
//        val movieList = listOf(Movie(1, "Movie 1", "poster_path", 100,"backdrop_path","",8.5))
//        `when`(apiMovieNowPlaying.getNowPlayingMovies(1)).thenReturn(MovieResponse(movieList))
//
//        viewModel.nowPlayingMovies.observeForever(observer)
//        viewModel.nowPlayingMovies.removeObserver(observer)
//    }
//
//    @Test
//    fun testUpComingMovies() = runBlocking {
//        val movieList = listOf(Movie(1, "Movie 1", "poster_path", 100,"backdrop_path","",8.5))
//        `when`(apiMovieUpComing.getUpComingMovies(1)).thenReturn(MovieResponse(movieList))
//
////        viewModel.upcomingMovies.observeForever(observer)
////        viewModel.upcomingMovies.removeObserver(observer)
//    }
//}
