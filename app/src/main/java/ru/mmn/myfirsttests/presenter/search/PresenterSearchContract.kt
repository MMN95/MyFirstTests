package ru.mmn.myfirsttests.presenter.search

import ru.mmn.myfirsttests.presenter.PresenterContract


internal interface PresenterSearchContract : PresenterContract {
    fun searchGitHub(searchQuery: String)
}

