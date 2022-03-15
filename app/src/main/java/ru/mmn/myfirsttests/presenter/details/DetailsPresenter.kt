package ru.mmn.myfirsttests.presenter.details

import ru.mmn.myfirsttests.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
    private val viewContract: ViewDetailsContract
) : PresenterDetailsContract {
}