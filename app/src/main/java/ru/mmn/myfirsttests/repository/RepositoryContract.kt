package ru.mmn.myfirsttests.repository

import ru.mmn.myfirsttests.repository.RepositoryCallback

internal interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )
}

