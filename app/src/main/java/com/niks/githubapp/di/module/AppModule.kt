package com.niks.githubapp.di.module

import com.google.gson.Gson
import com.niks.githubapp.di.scope.PerApplication
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        NetModule::class,
        ActivitiesModule::class
    ]
)
class AppModule {

    @Provides
    @PerApplication
    internal fun provideGson(): Gson {
        return Gson()
    }
}