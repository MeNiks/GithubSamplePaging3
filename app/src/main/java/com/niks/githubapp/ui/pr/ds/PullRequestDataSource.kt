package com.niks.githubapp.ui.pr.ds

import androidx.paging.PagingData
import com.niks.githubapp.network.vo.PullRequestVo
import com.niks.githubapp.recyclerview.RecyclerViewItemVo
import kotlinx.coroutines.flow.Flow

interface PullRequestDataSource {

    fun getClosedPullRequestFlow(orgName: String, repoName: String): Flow<PagingData<RecyclerViewItemVo<String>>>

    suspend fun getClosedPullRequest(
        orgName: String,
        repoName: String,
        page: Long,
        state: String,
        limit: Int
    ): List<PullRequestVo>
}