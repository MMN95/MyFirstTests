package ru.mmn.myfirsttests.repository

import retrofit2.Response
import ru.mmn.myfirsttests.model.SearchResponse

interface RepositoryCallback {
    fun handleGitHubResponse(response: Response<SearchResponse?>?)
    fun handleGitHubError()
}