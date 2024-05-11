package com.kinopoisk.matrixapp
import android.app.Application
import android.content.Context
import com.kinopoisk.matrixapp.di.AppComponent
import com.kinopoisk.matrixapp.di.DaggerAppComponent

class MatrixApp : Application() {
    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent)

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.builder()
            .application(this)
            .create()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MatrixApp -> appComponent
        else -> (applicationContext as MatrixApp).appComponent
    }
