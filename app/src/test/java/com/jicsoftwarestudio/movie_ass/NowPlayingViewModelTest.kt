package com.jicsoftwarestudio.movie_ass

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jicsoftwarestudio.model.Movie
import com.jicsoftwarestudio.model.dataRemote.RetrofitInstance
import com.jicsoftwarestudio.viewModel.NowPlayingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class NowPlayingViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<Movie>>
    private lateinit var viewModel: NowPlayingViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = NowPlayingViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchNowPlayingMovies with success response`() = runTest {
        val movies = listOf(Movie(1, "title_1", "ácc",1223,""), Movie(12, "title_2", "ăsasa",122,""))
        `when`(RetrofitInstance.apiMovieNowPlaying.getNowPlayingMovies().results).thenReturn(movies)

        viewModel.nowPlayingMovies.observeForever(observer)
        viewModel.fetchNowPlayingMovies()

        verify(observer).onChanged(movies)
    }

    @Test
    fun `fetchNowPlayingMovies with error response`() = runTest {
        val movies = listOf(Movie(1, "title_1", "ácc",1223,""), Movie(12, "title_2", "ăsasa",122,""))
        `when`(RetrofitInstance.apiMovieNowPlaying.getNowPlayingMovies().results).thenReturn(movies)

        viewModel.nowPlayingMovies.observeForever(observer)
        viewModel.fetchNowPlayingMovies()

        verify(observer).onChanged(movies)
    }
}