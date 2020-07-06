package com.ashish.baseproject.di

import com.ashish.baseproject.network.RepositoryImplementation
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val repoImplementation = module {
    factory { RepositoryImplementation(get(),androidApplication()) }
}
