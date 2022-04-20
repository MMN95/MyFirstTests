package ru.mmn.myfirsttests

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response
import ru.mmn.myfirsttests.model.SearchResponse
import ru.mmn.myfirsttests.model.SearchResult
import ru.mmn.myfirsttests.presenter.search.SearchPresenter
import ru.mmn.myfirsttests.repository.GitHubRepository
import ru.mmn.myfirsttests.view.search.ViewSearchContract

class SearchPresenterTest {

    private lateinit var presenter: SearchPresenter

    @Mock
    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var viewContract: ViewSearchContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchPresenter(viewContract, repository)
    }

    @Test
    fun searchGitHub_Test() {
        presenter.searchGitHub(TEST_PRESENTER_SEARCH_QUERY)
        verify(repository, times(1)).searchGithub(TEST_PRESENTER_SEARCH_QUERY, presenter)
    }

    @Test
    fun handleGitHubError_Test() {
        presenter.handleGitHubError()
        verify(viewContract, times(1)).displayError()
    }

    @Test
    fun handleGitHubResponse_ResponseUnsuccessful() {
        val response = mock(Response::class.java) as Response<SearchResponse?>
        `when`(response.isSuccessful).thenReturn(false)
        assertFalse(response.isSuccessful)
    }

    @Test
    fun handleGitHubResponse_Failure() {
        val response = mock(Response::class.java) as Response<SearchResponse?>
        `when`(response.isSuccessful).thenReturn(false)
        presenter.handleGitHubResponse(response)
        verify(viewContract, times(1))
            .displayError(TEST_DISPLAY_ERROR_RESPONSE_FAILURE)
    }

    @Test
    fun handleGitHubResponse_ResponseFailure_ViewContractMethodOrder() {
        val response = mock(Response::class.java) as Response<SearchResponse?>
        `when`(response.isSuccessful).thenReturn(false)
        presenter.handleGitHubResponse(response)
        val inOrder = inOrder(viewContract)
        inOrder.verify(viewContract).displayLoading(false)
        inOrder.verify(viewContract).displayError(TEST_DISPLAY_ERROR_RESPONSE_FAILURE)
    }

    @Test
    fun handleGitHubResponse_ResponseIsEmpty() {
        val response = mock(Response::class.java) as Response<SearchResponse?>
        `when`(response.body()).thenReturn(null)
        presenter.handleGitHubResponse(response)
        assertNull(response.body())
    }

    @Test
    fun handleGitHubResponse_ResponseIsNotEmpty() {
        val response = mock(Response::class.java) as Response<SearchResponse?>
        `when`(response.body()).thenReturn(mock(SearchResponse::class.java))
        presenter.handleGitHubResponse(response)
        assertNotNull(response.body())
    }

    @Test
    fun handleGitHubResponse_EmptyResponse() {
        val response = mock(Response::class.java) as Response<SearchResponse?>
        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(null)
        presenter.handleGitHubResponse(response)
        verify(viewContract, times(1))
            .displayError(TEST_DISPLAY_ERROR_EMPTY_RESPONSE)
    }

    @Test
    fun handleGitHubResponse_Success() {
        val response = mock(Response::class.java) as Response<SearchResponse?>
        val searchResponse = mock(SearchResponse::class.java)
        val searchResults = listOf(mock(SearchResult::class.java))

        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(searchResponse)
        `when`(searchResponse.searchResults).thenReturn(searchResults)
        `when`(searchResponse.totalCount).thenReturn(TEST_SEARCH_RESULTS_COUNT)

        presenter.handleGitHubResponse(response)
        verify(viewContract, times(1)).displaySearchResults(searchResults,
            TEST_SEARCH_RESULTS_COUNT)
    }

    @Test
    fun onAttach_Test() {
        presenter.onAttach(viewContract)
        presenter.handleGitHubError()
        verify(viewContract, times(1)).displayError()
    }

    @Test
    fun onDetach_Test() {
        presenter.onDetach()
        presenter.handleGitHubError()
        verify(viewContract, times(0)).displayError()
    }
}
