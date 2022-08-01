package com.niks.githubapp.di.module

import com.niks.githubapp.ui.pr.ds.PullRequestDataSourceModule
import dagger.Module

@Module(
    includes = [
        PullRequestDataSourceModule::class
    ]
)
abstract class DataSourceProviderModule