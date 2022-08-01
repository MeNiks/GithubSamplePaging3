package com.niks.githubapp.ui.pr.ds

import androidx.paging.PagingData
import com.niks.githubapp.network.GithubApi
import com.niks.githubapp.network.vo.PullRequestVo
import com.niks.githubapp.recyclerview.RecyclerViewItemVo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PullRequestRemoteDataSource
@Inject
constructor(
    private val githubApi: GithubApi
) : PullRequestDataSource {

    override fun getClosedPullRequestFlow(orgName: String, repoName: String): Flow<PagingData<RecyclerViewItemVo<String>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getClosedPullRequest(
        orgName: String,
        repoName: String,
        page: Long,
        state: String,
        limit: Int
    ): List<PullRequestVo> {
        return githubApi.getPullRequestList(
            orgName,
            repoName,
            page,
            state,
            limit
        )
    }
}