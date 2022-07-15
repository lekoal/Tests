package com.geekbrains.tests

import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.ViewDetailsContract
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var view: ViewDetailsContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailsPresenter()
    }

    @Test
    fun onAttach_Success() {
        assertNotSame(view, presenter.getView())
        presenter.onAttach(view)
        assertSame(view, presenter.getView())
    }

    @Test
    fun onDetach_Success() {
        presenter.onAttach(view)
        assertSame(view, presenter.getView())
        presenter.onDetach()
        assertNotSame(view, presenter.getView())
    }

    @Test
    fun onIncrement_Success() {
        presenter.onAttach(view)
        presenter.onIncrement()
        verify(view).setCount(anyInt())
    }

    @Test
    fun onDecrement_Success() {
        presenter.onAttach(view)
        presenter.onDecrement()
        verify(view).setCount(anyInt())
    }

    @Test
    fun setCounter_Success() {
        presenter.onAttach(view)
        var count = 4
        presenter.setCounter(count)
        assertEquals(count, presenter.getCurrentCount())
        count = 8
        presenter.setCounter(count)
        assertEquals(count, presenter.getCurrentCount())
    }
}