package com.hoangpd15.smartmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hoangpd15.smartmovie.model.Movie
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PopularViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<Movie>>

    private lateinit var viewModel: PopularViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = PopularViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchPopularMovies with success response`() = runTest {
        val movies = listOf(Movie(1, "title_1", "ácc",1223,"overview","backdrop_path",8.5), Movie(12, "title_2", "ăsasa",122,"overview","backdrop_path",8.5))
        `when`(RetrofitInstance.apiMoviePopular.getPopularMovies(1).results).thenReturn(movies)

        viewModel.popularMovies.observeForever(observer)
//        viewModel.fetchPopularMovies()

        verify(observer).onChanged(movies)
    }

    @Test
    fun `fetchPopularMovies with error response`() = runTest {
        `when`(RetrofitInstance.apiMovieUpComing.getUpComingMovies(1).results).thenThrow(Exception("Network error"))

        viewModel.popularMovies.observeForever(observer)
//        viewModel.fetchPopularMovies()

        verify(observer).onChanged(emptyList())
    }
}