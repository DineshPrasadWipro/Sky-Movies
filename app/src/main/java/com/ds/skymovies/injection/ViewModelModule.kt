package com.ds.skymovies.injection

import com.ds.skymovies.viewmodel.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {

    viewModel {
        MovieListViewModel(get())
    }
}
