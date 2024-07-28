package com.hoangpd15.smartmovie.ui.homeScreen.typeMovieScreen
import com.example.domain.DeleteFavoriteMovieUseCase
import com.example.domain.GetNowPlayingMoviesUseCase
import com.example.domain.GetPopularMoviesUseCase
import com.example.domain.GetTopRateMoviesUseCase
import com.example.domain.GetUpComingMoviesUseCase
import com.example.domain.InsertFavoriteMovieUseCase
import com.example.domain.entities.Movie
import com.hoangpd15.smartmovie.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val nowPlayingUseCase: GetNowPlayingMoviesUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
) : BaseViewModel(deleteFavoriteMovieUseCase,insertFavoriteMovieUseCase) {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return nowPlayingUseCase(page).results
    }
}

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val popularUseCase: GetPopularMoviesUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
) : BaseViewModel(deleteFavoriteMovieUseCase,insertFavoriteMovieUseCase) {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return popularUseCase(page).results
    }
}

@HiltViewModel
class TopRatedViewModel @Inject constructor(
    private val topRatedUseCase: GetTopRateMoviesUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
) : BaseViewModel(deleteFavoriteMovieUseCase,insertFavoriteMovieUseCase) {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return topRatedUseCase(page).results
    }
}

@HiltViewModel
class UpComingViewModel @Inject constructor(
    private val upComingUseCase: GetUpComingMoviesUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
) : BaseViewModel(deleteFavoriteMovieUseCase,insertFavoriteMovieUseCase) {
    override suspend fun fetchMoviesFromApi(page: Int): List<Movie> {
        return upComingUseCase(page).results
    }
}