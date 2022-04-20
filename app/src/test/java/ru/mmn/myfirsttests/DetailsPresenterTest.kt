package ru.mmn.myfirsttests

import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.mmn.myfirsttests.presenter.details.DetailsPresenter
import ru.mmn.myfirsttests.view.details.ViewDetailsContract

class DetailsPresenterTest {

    private lateinit var detailsPresenter: DetailsPresenter

    @Mock
    private lateinit var viewDetailsContract: ViewDetailsContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailsPresenter = DetailsPresenter(null)
        detailsPresenter.onAttach(viewDetailsContract)
    }

    @Test
    fun setCounter_Test() {
        detailsPresenter.setCounter(1)
        detailsPresenter.onIncrement()
        verify(viewDetailsContract, atLeastOnce()).setCount(2)
    }

    @Test
    fun onIncrement_Test() {
        detailsPresenter.onIncrement()
        verify(viewDetailsContract, times(1)).setCount(1)
    }

    @Test
    fun onDecrement_Test() {
        detailsPresenter.onDecrement()
        verify(viewDetailsContract, times(1)).setCount(-1)
    }

    @Test
    fun onAttach_Test() {
        detailsPresenter.onAttach(viewDetailsContract)
        detailsPresenter.onIncrement()
        verify(viewDetailsContract, times(1)).setCount(1)
    }

    @Test
    fun onDetach_Test() {
        detailsPresenter.onDetach()
        detailsPresenter.onIncrement()
        verify(viewDetailsContract, times(0)).setCount(1)
    }

}