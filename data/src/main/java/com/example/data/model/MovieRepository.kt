package com.example.data.model

import com.example.data.model.dataLocal.AppDatabase
import com.example.data.model.dataRemote.RetrofitInstance
import com.example.domain.IMovieRepository
import com.example.domain.entities.CastMovie
import com.example.domain.entities.FavoriteMovieEntity
import com.example.domain.entities.GenresMovies
import com.example.domain.entities.MovieDetail
import com.example.domain.entities.Movies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) : IMovieRepository {
    override suspend fun getPopularMovies(page: Int): Movies {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMoviePopular.getPopularMovies(page)
        }
    }

    override suspend fun getTopRateMovies(page: Int): Movies {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMovieTopRate.getTopRateMovies(page)
        }
    }

    override suspend fun getUpComingMovies(page: Int): Movies {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMovieUpComing.getUpComingMovies(page)
        }
    }

    override suspend fun getNowPlayingMovies(page: Int): Movies {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMovieNowPlaying.getNowPlayingMovies(page)
        }
    }

    override suspend fun getSearchMovies(query: String): Movies {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMovieSearch.searchMovies(query)
        }
    }

    override suspend fun getMoviesByGenre(genreId: Int): Movies {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiAllMovie.getMoviesByGenre(genreId)
        }
    }

    override suspend fun getListGenres(): GenresMovies {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiListGenres.getListGenres()
        }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetail {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiMovieDetail.getMovieDetails(movieId)
        }
    }

    override suspend fun getCastMovie(movieId: Int): CastMovie {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.apiCastMovie.getCastMovie(movieId)
        }
    }

    override suspend fun getInsert(movie: FavoriteMovieEntity) {
        return withContext(Dispatchers.IO) {
            appDatabase.favoriteMovieDao().insert(movie)
        }
    }

    override suspend fun getDelete(movieId: Int) {
        return withContext(Dispatchers.IO) {
            appDatabase.favoriteMovieDao().deleteById(movieId)
        }
    }

    override suspend fun getAllFavoriteMovies(): List<FavoriteMovieEntity> {
        return withContext(Dispatchers.IO) {
            appDatabase.favoriteMovieDao().getAllFavoriteMovies()
        }
    }

    override suspend fun isFavoriteMovie(movieId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            appDatabase.favoriteMovieDao().isFavoriteMovie(movieId)
        }
    }
}