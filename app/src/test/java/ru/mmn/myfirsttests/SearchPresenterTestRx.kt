package ru.mmn.myfirsttests

import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import ru.mmn.myfirsttests.model.SearchResponse
import ru.mmn.myfirsttests.presenter.search.SearchPresenter
import ru.mmn.myfirsttests.repository.GitHubRepository
import ru.mmn.myfirsttests.stubs.ScheduleProviderStub
import ru.mmn.myfirsttests.view.search.ViewSearchContract

class SearchPresenterTestRx {

    private lateinit var presenter: SearchPresenter

    @Mock
    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var viewContract: ViewSearchContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchPresenter(viewContract, repository, ScheduleProviderStub())
    }

    @Test
    fun searchGitHub_Test() {
        `when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.just(
                SearchResponse(
                    1,
                    listOf()
                )
            )
        )
        presenter.searchGitHub(SEARCH_QUERY)
        verify(repository, times(1)).searchGithub(SEARCH_QUERY)
    }

    @Test
    fun handleRequestError_Test() {
        `when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.error(Throwable(ERROR_TEXT))
        )
        presenter.searchGitHub(SEARCH_QUERY)
        verify(viewContract, times(1)).displayError("error")
    }

    @Test
    fun handleResponseError_TotalCountIsNull() {
        `when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.just(
                SearchResponse(
                    null,
                    listOf()
                )
            )
        )
        presenter.searchGitHub(SEARCH_QUERY)
        verify(viewContract, times(1)).displayError("Search results or total count are null")
    }

    @Test
    fun handleResponseError_TotalCountIsNull_ViewContractMethodOrder() {
        `when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.just(
                SearchResponse(
                    null,
                    listOf()
                )
            )
        )
        presenter.searchGitHub(SEARCH_QUERY)
        val inOrder = inOrder(viewContract)
        inOrder.verify(viewContract).displayLoading(true)
        inOrder.verify(viewContract).displayError("Search results or total count are null")
                    inOrder.verify(viewContract).displayLoading(false)
    }

    @Test
    fun handleResponseSuccess() {
        `when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.just(
                SearchResponse(
                    42,
                    listOf()
                )
            )
        )
        presenter.searchGitHub(SEARCH_QUERY)
        verify(viewContract, times(1)).displaySearchResults(listOf(), 42)
    }

    companion object {
        private const val SEARCH_QUERY = "some query"
        private const val ERROR_TEXT = "error"
    }
}