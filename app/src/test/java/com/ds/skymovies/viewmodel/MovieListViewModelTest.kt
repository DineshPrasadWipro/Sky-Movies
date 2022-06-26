package com.ds.skymovies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ds.skymovies.TestBase
import com.ds.skymovies.model.CustomMovie
import com.ds.skymovies.model.Movie
import com.ds.skymovies.model.Movies
import com.ds.skymovies.network.NetworkInterface
import com.ds.skymovies.repository.MockNetworkRepository
import com.ds.skymovies.repository.NetworkRepository
import com.ds.skymovies.util.NetworkResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkClass
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.core.Every
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.net.SocketTimeoutException

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
        var expected = mockNetworkRepository.movies.data.size
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