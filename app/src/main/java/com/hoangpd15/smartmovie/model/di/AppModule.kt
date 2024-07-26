package com.hoangpd15.smartmovie.model.di

import com.hoangpd15.smartmovie.doumain.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideMovieRepository(): MovieRepository {
        return MovieRepository()
    }
}