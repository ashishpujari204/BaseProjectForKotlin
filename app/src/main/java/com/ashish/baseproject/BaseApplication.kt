package com.ashish.baseproject

import android.app.Application
import com.ashish.baseproject.di.repoImplementation
import com.ashish.baseproject.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger(Level.DEBUG)
            modules(
                listOf(
                    retrofitModule, repoImplementation
                )
            )
        }
    }
}