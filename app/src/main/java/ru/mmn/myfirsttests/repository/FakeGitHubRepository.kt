package ru.mmn.myfirsttests.repository

import retrofit2.Response
import ru.mmn.myfirsttests.model.SearchResponse

internal class FakeGitHubRepository : RepositoryContract {

    override fun searchGithub(
        query: String,
        callback: RepositoryCallback
    ) {
        callback.handleGitHubResponse(Response.success(SearchResponse(42, listOf())))
    }
}