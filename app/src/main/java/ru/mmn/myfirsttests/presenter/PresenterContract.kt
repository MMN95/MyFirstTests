package ru.mmn.myfirsttests.presenter

import ru.mmn.myfirsttests.view.ViewContract

internal interface PresenterContract {
    fun onAttach(viewContract: ViewContract)
    fun onDetach()
}
