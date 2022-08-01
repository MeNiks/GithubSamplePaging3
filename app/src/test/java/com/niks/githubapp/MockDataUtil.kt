package com.niks.githubapp

import com.niks.githubapp.network.vo.PullRequestVo
import java.util.Date

object MockDataUtil {

    fun getPullRequestVo(id: String = "1", title: String = "Pr Title"): PullRequestVo {
        return PullRequestVo(
            id = id,
            title = title,
            created_at = Date(),
            closed_at = null,
            user = null
        )
    }
}