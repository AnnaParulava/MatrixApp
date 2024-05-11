package com.kinopoisk.matrixapp.di

import android.app.Application
import com.kinopoisk.matrixapp.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
      DataModule::class,
    ]
)

interface AppComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun create(): AppComponent
    }
}