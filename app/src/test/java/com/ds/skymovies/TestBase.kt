package com.ds.skymovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.clearAllMocks
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.stopKoin


abstract class TestBase {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    open fun setup() {
        clearAllMocks()
    }

    @After
    open fun tearDown() {
        stopKoin()
    }

}
