package com.niks.githubapp.ui.pr.ds

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.niks.githubapp.di.qualifier.Local
import com.niks.githubapp.di.qualifier.Remote
import com.niks.githubapp.network.vo.PullRequestVo
import com.niks.githubapp.recyclerview.RecyclerViewItemVo
import com.niks.githubapp.ui.pr.PullRequestPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PullRequestRepository
@Inject
constructor(
    @Local
    val pullRequestLocalDataSource: PullRequestDataSource,
    @Remote
    val pullRequestRemoteDataSource: PullRequestDataSource,
) : PullRequestDataSource {

    override suspend fun getClosedPullRequest(
        orgName: String,
        repoName: String,
        page: Long,
        state: String,
        limit: Int
    ): List<PullRequestVo> {
        return pullRequestRemoteDataSource.getClosedPullRequest(
            orgName = orgName,
            repoName = repoName,
            page = page,
            state = state,
            limit = limit
        )
    }

    override fun getClosedPullRequestFlow(
        orgName: String,
        repoName: String
    ): Flow<PagingData<RecyclerViewItemVo<String>>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PullRequestPagingSource(
                    orgName = orgName,
                    repoName = repoName,
                    pullRequestDataSource = pullRequestRemoteDataSource
                )
            }
        )
            .flow
    }

    companion object {
        private const val INITIAL_PAGE_SIZE = 50
        private const val PAGE_SIZE = 20
    }
}