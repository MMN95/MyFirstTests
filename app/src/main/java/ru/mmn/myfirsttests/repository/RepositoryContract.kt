package ru.mmn.myfirsttests.repository

import io.reactivex.Observable
import ru.mmn.myfirsttests.model.SearchResponse

internal interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )

    fun searchGithub(
        query: String
    ): Observable<SearchResponse>

}

