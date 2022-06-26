package com.ds.skymovies

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.ds.skymovies.model.CustomMovie
import com.ds.skymovies.model.Movies
import com.ds.skymovies.ui.MovieListFragment
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Dinesh on 24/06/2022.
 * Disable init block in MovieListViewModel inorder to make this tests pass
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MovieFragmentTest {

    private lateinit var customMovies: MutableList<CustomMovie>
    private var moviesFragment: MovieListFragment = MovieListFragment()

    private var gson: Gson = Gson()
    private lateinit var movies: Movies

    @Before
    fun init() {
        launchFragmentInContainer {
            moviesFragment
        }.onFragment {

        }
        movies = gson.fromJson(TestData.moviesSampleJson, Movies::class.java)
        setValues(movies)
        moviesFragment.setList(customMovies)

    }

    @Test
    fun check_search_text_is_displayed() {
        onView(withId(R.id.movies_search))
            .check(matches(isDisplayed()))
        onView(withId(R.id.movies_search)).perform(typeText("Movies"))
    }

    @Test
    fun check_list_view_is_displayed() {
        onView(withId(R.id.movieList))
            .check(matches(isDisplayed()))

    }

    @Test
    fun check_search_item_is_displayed() {
        onView(withId(R.id.movies_search)).perform(typeText("my"))
        onView(withText("Mystery"))
            .check(matches(isDisplayed()))
    }

    private fun setValues(movies: Movies) {
        customMovies = mutableListOf()
        movies.data.forEach() { movie ->
            val customMovie =
                CustomMovie(movie.id, movie.title, movie.year, movie.genre, movie.poster)
            customMovies.add(customMovie)
        }
    }
}
