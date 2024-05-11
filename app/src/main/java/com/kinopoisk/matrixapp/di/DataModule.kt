package com.kinopoisk.matrixapp.di

import repository.MatrixRepository
import MatrixRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideMatrixRepository(): MatrixRepository {
        return MatrixRepositoryImpl()
    }
}