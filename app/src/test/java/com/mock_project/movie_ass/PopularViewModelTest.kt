package com.mock_project.movie_ass

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mock_project.model.Movie
import com.mock_project.model.dataRemote.RetrofitInstance
import com.mock_project.viewModel.homeViewModel.PopularViewModel
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
        `when`(RetrofitInstance.apiMoviePopular.getPopularMovies().results).thenReturn(movies)

        viewModel.popularMovies.observeForever(observer)
        viewModel.fetchPopularMovies()

        verify(observer).onChanged(movies)
    }

    @Test
    fun `fetchPopularMovies with error response`() = runTest {
        `when`(RetrofitInstance.apiMovieUpComing.getUpComingMovies().results).thenThrow(Exception("Network error"))

        viewModel.popularMovies.observeForever(observer)
        viewModel.fetchPopularMovies()

        verify(observer).onChanged(emptyList())
    }
}