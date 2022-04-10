package ru.mmn.myfirsttests.repository

import io.reactivex.Observable
import ru.mmn.myfirsttests.model.SearchResponse

interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )

    fun searchGithub(
        query: String
    ): Observable<SearchResponse>

    suspend fun searchGithubAsync(
        query: String
    ): SearchResponse

}

