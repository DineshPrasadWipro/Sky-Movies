package com.ds.skymovies

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.ds.skymovies.model.CustomMovie
import com.ds.skymovies.ui.MovieListFragment
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

    lateinit var customMovies: MutableList<CustomMovie>
    private lateinit var moviesFragment: MovieListFragment

    @Before
    fun init() {
        moviesFragment = MovieListFragment()
        moviesFragment.adapter?.setMovieList(customMovies)

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
}