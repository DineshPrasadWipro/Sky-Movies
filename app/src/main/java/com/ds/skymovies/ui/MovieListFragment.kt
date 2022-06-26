package com.ds.skymovies.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ds.skymovies.R
import com.ds.skymovies.adapter.MovieListAdapter
import com.ds.skymovies.databinding.FragmentMovieListBinding

import com.ds.skymovies.model.CustomMovie
import com.ds.skymovies.util.RecyclerItemDecor
import com.ds.skymovies.viewmodel.MovieListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieListFragment : BaseFragment() {
    private var _binding: FragmentMovieListBinding? = null

    lateinit var adapter: MovieListAdapter
    private var recyclerView: RecyclerView? = null
    private var movieList: List<CustomMovie> = mutableListOf()
    private val movieListViewModel: MovieListViewModel by viewModel()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = root.findViewById(R.id.movieList)
        binding.vm = movieListViewModel
        adapter = MovieListAdapter(emptyList(), requireContext())
        recyclerView?.setup()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    //initialize components
    private fun init() {

        movieListViewModel.run {

            movieList.observe {
                setList(it)

            }

            loading.observe {
                if (it) showProgress() else cancelProgress()
            }

            errorMessage.observe {
                cancelProgress()
                Toast.makeText(requireContext().applicationContext, it, Toast.LENGTH_SHORT).show()
            }

            searchText.observe(viewLifecycleOwner) {
                it?.let { adapter?.filter?.filter(it) }
            }
        }

    }

    //setting list view adapter
    private fun setList(list: List<CustomMovie>) {
        movieList = list
        adapter = MovieListAdapter(list, requireContext())
        recyclerView?.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun RecyclerView.setup() {
        addItemDecoration(
            RecyclerItemDecor(
                resources.getDimension(R.dimen.item_space).toInt()
            )
        )
        layoutManager = GridLayoutManager(
            context,
            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> DEFAULT_GRID_COLUMNS
                Configuration.ORIENTATION_LANDSCAPE -> LANDSCAPE_GRID_COLUMNS
                else -> DEFAULT_GRID_COLUMNS
            }
        )
        adapter = adapter
    }

    companion object {
        private const val DEFAULT_GRID_COLUMNS = 3
        private const val LANDSCAPE_GRID_COLUMNS = 5
    }

}