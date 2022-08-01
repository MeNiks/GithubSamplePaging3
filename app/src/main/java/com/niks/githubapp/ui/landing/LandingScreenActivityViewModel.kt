package com.niks.githubapp.ui.landing

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.niks.githubapp.R

class LandingScreenActivityViewModel : ViewModel() {

    @DrawableRes
    fun validateAndGetErrorMessage(orgName: String?, repoNameString: String?): Int? {
        if (orgName.isNullOrBlank())
            return R.string.mandatory_org_name

        if (repoNameString.isNullOrBlank())
            return R.string.mandatory_repo_name
        return null
    }

}