package ru.mmn.myfirsttests.view.details

import ru.mmn.myfirsttests.view.ViewContract

internal interface ViewDetailsContract : ViewContract {
    fun setCount(count: Int)
}