package com.example.domain

import com.example.domain.entities.CastMovie
import com.example.domain.entities.FavoriteMovieEntity
import com.example.domain.entities.GenresMovies
import com.example.domain.entities.MovieDetail
import com.example.domain.entities.Movies
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(page: Int): Movies {
        return movieRepository.getPopularMovies(page)
    }
}

class GetTopRateMoviesUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(page: Int): Movies {
        return movieRepository.getTopRateMovies(page)
    }
}

class GetUpComingMoviesUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(page: Int): Movies {
        return movieRepository.getUpComingMovies(page)
    }
}

class GetNowPlayingMoviesUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(page: Int): Movies {
        return movieRepository.getNowPlayingMovies(page)
    }
}

class GetSearchMoviesUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(query: String): Movies {
        return movieRepository.getSearchMovies(query)
    }
}

class GetMoviesByGenreUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(genreId: Int): Movies {
        return movieRepository.getMoviesByGenre(genreId)
    }
}

class GetListGenresUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(): GenresMovies {
        return movieRepository.getListGenres()
    }
}

class MovieDetailUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(id: Int): MovieDetail {
        return movieRepository.getMovieDetails(id)
    }
}

class CastMovieUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(id: Int): CastMovie {
        return movieRepository.getCastMovie(id)
    }
}

class InsertFavoriteMovieUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(movie: FavoriteMovieEntity) {
        return movieRepository.getInsert(movie)
    }
}

class GetAllFavoriteMoviesUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(): List<FavoriteMovieEntity> {
        return movieRepository.getAllFavoriteMovies()
    }
}

class IsFavoriteMovieUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(movieId: Int): Boolean {
        return movieRepository.isFavoriteMovie(movieId)
    }
}

class DeleteFavoriteMovieUseCase @Inject constructor(private val movieRepository: IMovieRepository) {
    suspend operator fun invoke(movieId: Int) {
        return movieRepository.getDelete(movieId)
    }
}