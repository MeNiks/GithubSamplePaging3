package com.niks.githubapp.network

import com.niks.githubapp.network.vo.PullRequestVo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("/repos/{userName}/{repoName}/pulls")
    suspend fun getPullRequestList(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String,
        @Query("page") page: Long,
        @Query("state") state: String,
        @Query("per_page") limit: Int = 10
    ): List<PullRequestVo>
}

