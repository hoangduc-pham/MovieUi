package com.example.data.model.di
import android.content.Context
import com.example.data.model.dataLocal.AppDatabase
import com.example.data.model.dataLocal.FavoriteMovieDao
import com.example.data.model.domain.MovieRepositoryImpl
import com.example.domain.DeleteFavoriteMovieUseCase
import com.example.domain.GetAllFavoriteMoviesUseCase
import com.example.domain.GetNowPlayingMoviesUseCase
import com.example.domain.GetPopularMoviesUseCase
import com.example.domain.GetTopRateMoviesUseCase
import com.example.domain.GetUpComingMoviesUseCase
import com.example.domain.IMovieRepository
import com.example.domain.InsertFavoriteMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideMovieRepository(database: AppDatabase): IMovieRepository {
        return MovieRepositoryImpl(database)
    }

    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun insertFavoriteMovieUseCase(repository: IMovieRepository): InsertFavoriteMovieUseCase {
        return InsertFavoriteMovieUseCase(repository)
    }

    @Provides
    fun deleteFavoriteMovieUseCase(repository: IMovieRepository): DeleteFavoriteMovieUseCase {
        return DeleteFavoriteMovieUseCase(repository)
    }

    @Provides
    fun getPopularMoviesUseCase(repository: IMovieRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(repository)
    }

    @Provides
    fun getTopRateMoviesUseCase(repository: IMovieRepository): GetTopRateMoviesUseCase {
        return GetTopRateMoviesUseCase(repository)
    }

    @Provides
    fun getNowPlayingMoviesUseCase(repository: IMovieRepository): GetNowPlayingMoviesUseCase {
        return GetNowPlayingMoviesUseCase(repository)
    }

    @Provides
    fun getUpComingMoviesUseCase(repository: IMovieRepository): GetUpComingMoviesUseCase {
        return GetUpComingMoviesUseCase(repository)
    }
}