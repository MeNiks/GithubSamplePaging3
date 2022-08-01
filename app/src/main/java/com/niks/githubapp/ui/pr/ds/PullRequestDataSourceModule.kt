package com.niks.githubapp.ui.pr.ds

import com.niks.githubapp.di.qualifier.Local
import com.niks.githubapp.di.qualifier.Remote
import com.niks.githubapp.di.qualifier.Repository
import dagger.Binds
import dagger.Module

@Module
abstract class PullRequestDataSourceModule {
    @Binds
    @Local
    abstract fun bindPullRequestLocalDataSource(pullRequestLocalDataSource: PullRequestLocalDataSource): PullRequestDataSource

    @Binds
    @Remote
    abstract fun bindPullRequestRemoteDataSource(pullRequestRemoteDataSource: PullRequestRemoteDataSource): PullRequestDataSource

    @Binds
    @Repository
    abstract fun bindPullRequestRepository(pullRequestRepository: PullRequestRepository): PullRequestDataSource
}