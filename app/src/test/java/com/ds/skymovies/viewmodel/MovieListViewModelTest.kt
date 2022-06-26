package com.ds.skymovies.viewmodel

import com.ds.skymovies.TestBase
import com.ds.skymovies.repository.MockNetworkRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieListViewModelTest : TestBase() {

    private var mockNetworkRepository = MockNetworkRepository()

    private lateinit var viewModel: MovieListViewModel

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    override fun setup() {
        super.setup()
        Dispatchers.setMain(dispatcher)
        viewModel = MovieListViewModel(mockNetworkRepository)

    }

    @After
    override fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `checking list count`() {
        val expected = mockNetworkRepository.movies.data.size
        var actual = 0
        runBlocking {
            viewModel.movieList.observeForever {
                if (it != null) {
                    actual = it.size
                }
            }

        }
        viewModel.getMovies()
        Assert.assertEquals(expected, actual)
        mockNetworkRepository.setMovies()
        viewModel.getMovies()
        Assert.assertEquals(3, actual)

    }

    @Test
    fun `test search query changed posts search text`() {
        // given
        assertEquals(true, viewModel.onQueryChanged("text"))
        // then
        assertEquals("text", viewModel.searchText.value)
    }

    @Test
    fun `test retrieving sample movie list throws exception`() {
        // given
        mockNetworkRepository.setException()

        viewModel.getMovies()

        // then
        assertEquals(false, viewModel.loading.value)
        assertEquals(
            "Network Exception",
            viewModel.errorMessage.value
        )
    }

    @Test
    fun `test retrieving sample movie list throw error`() {
        // given
        mockNetworkRepository.setError()

        viewModel.getMovies()

        // then
        assertEquals(false, viewModel.loading.value)
        assertEquals(
            "404",
            viewModel.errorMessage.value
        )
    }
}