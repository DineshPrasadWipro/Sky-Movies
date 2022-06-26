package com.ds.skymovies

import android.app.Application
import com.ds.skymovies.injection.appModule
import com.ds.skymovies.injection.repositoryModule
import com.ds.skymovies.injection.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SkyMoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {

        val appModules = listOf(
            appModule,
            repositoryModule,
            vmModule
        )

        startKoin {
            androidContext(this@SkyMoviesApplication)
            modules(appModules)
        }
    }


}