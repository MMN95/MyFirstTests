package ru.mmn.myfirsttests.presenter.details

import ru.mmn.myfirsttests.view.ViewContract
import ru.mmn.myfirsttests.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
    private var viewContract: ViewDetailsContract? = null,
    private var count: Int = 0
) : PresenterDetailsContract {
    override fun setCounter(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewContract?.setCount(count)
    }

    override fun onDecrement() {
        count--
        viewContract?.setCount(count)
    }

    override fun onAttach(viewContract: ViewContract) {
        this.viewContract = viewContract as ViewDetailsContract
    }

    override fun onDetach() {
        viewContract = null
    }
}