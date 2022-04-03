package ru.mmn.myfirsttests

import okhttp3.Request
import okio.Timeout
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mmn.myfirsttests.model.SearchResponse
import ru.mmn.myfirsttests.repository.GitHubApi
import ru.mmn.myfirsttests.repository.GitHubRepository
import ru.mmn.myfirsttests.repository.RepositoryCallback

class GitHubRepositoryTest {

    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var gitHubApi: GitHubApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = GitHubRepository(gitHubApi)
    }

    @Test
    fun searchGithub_Test() {
        val call = mock(Call::class.java) as Call<SearchResponse?>

        `when`(gitHubApi.searchGithub(TEST_REPOSITORY_SEARCH_QUERY)).thenReturn(call)
        repository.searchGithub(TEST_REPOSITORY_SEARCH_QUERY, mock(RepositoryCallback::class.java))
        verify(gitHubApi, times(1)).searchGithub(TEST_REPOSITORY_SEARCH_QUERY)
    }

    @Test
    fun searchGithub_TestCallback() {
        val response = mock(Response::class.java) as Response<SearchResponse?>
        val gitHubRepositoryCallBack = mock(RepositoryCallback::class.java)

        val call = object : Call<SearchResponse?> {
            override fun enqueue(callback: Callback<SearchResponse?>) {
                callback.onResponse(this, response)
                callback.onFailure(this, Throwable())
            }

            override fun clone(): Call<SearchResponse?> {
                TODO("Not yet implemented")
            }

            override fun execute(): Response<SearchResponse?> {
                TODO("Not yet implemented")
            }

            override fun isExecuted(): Boolean {
                TODO("Not yet implemented")
            }

            override fun cancel() {
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun request(): Request {
                TODO("Not yet implemented")
            }

            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }
        }

        `when`(gitHubApi.searchGithub(TEST_REPOSITORY_SEARCH_QUERY)).thenReturn(call)
        repository.searchGithub(TEST_REPOSITORY_SEARCH_QUERY, gitHubRepositoryCallBack)

        verify(gitHubRepositoryCallBack, times(1)).handleGitHubResponse(response)
        verify(gitHubRepositoryCallBack, times(1)).handleGitHubError()
    }

    @Test
    fun searchGithub_TestCallback_WithMock() {
        val call = mock(Call::class.java) as Call<SearchResponse?>
        val callBack = mock(Callback::class.java) as Callback<SearchResponse?>
        val gitHubRepositoryCallBack = mock(RepositoryCallback::class.java)
        val response = mock(Response::class.java) as Response<SearchResponse?>

        `when`(gitHubApi.searchGithub(TEST_REPOSITORY_SEARCH_QUERY)).thenReturn(call)
        `when`(call.enqueue(callBack)).then {
            callBack.onResponse(any(), any())
        }
        `when`(callBack.onResponse(any(), any())).then {
            gitHubRepositoryCallBack.handleGitHubResponse(response)
        }
        repository.searchGithub(TEST_REPOSITORY_SEARCH_QUERY, gitHubRepositoryCallBack)
        verify(gitHubRepositoryCallBack, times(1)).handleGitHubResponse(response)
    }
}
