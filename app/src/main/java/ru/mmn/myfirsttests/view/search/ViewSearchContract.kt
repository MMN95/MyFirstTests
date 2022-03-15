package ru.mmn.myfirsttests.view.search

import ru.mmn.myfirsttests.model.SearchResult
import ru.mmn.myfirsttests.view.ViewContract


internal interface ViewSearchContract : ViewContract {
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}


