package ru.mmn.myfirsttests.presenter.details

import ru.mmn.myfirsttests.presenter.PresenterContract

internal interface PresenterDetailsContract : PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}

