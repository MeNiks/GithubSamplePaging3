package com.niks.githubapp.di

import android.app.Application
import com.niks.githubapp.GithubApplication
import com.niks.githubapp.di.module.AppModule
import com.niks.githubapp.di.module.DataSourceProviderModule
import com.niks.githubapp.di.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@PerApplication
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        DataSourceProviderModule::class
    ]
)
interface AppComponent {

    fun inject(app: GithubApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}