package com.ds.skymovies.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ds.skymovies.R
import com.ds.skymovies.model.CustomMovie

class MovieListAdapter constructor(
    movieList: List<CustomMovie>, context: Context
) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>(), Filterable {

    private var mData: MutableList<CustomMovie> = mutableListOf()
    private var adapterItems: MutableList<CustomMovie> = mutableListOf()
    private var mContext: Context

    init {
        setMovieList(movieList)
        mContext = context

    }

    private fun setMovieList(movies: List<CustomMovie>) {
        mData.clear()
        mData.addAll(movies)
        setData(mData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.genre.text = adapterItems[position].genre
            Glide.with(mContext)
                .load(adapterItems[position].poster)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.movieImage)
        } catch (exception: Exception) {
            Log.d("Exception", exception.toString())
        }
    }

    override fun getItemCount(): Int {
        return adapterItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setData(data: List<CustomMovie>) {
        adapterItems.clear()
        adapterItems.addAll(data)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                return constraint?.toString()?.let { searchText ->
                    val filtered = mData.filter {
                        it.title.contains(searchText, ignoreCase = true) ||
                                it.genre.contains(searchText, ignoreCase = true)
                    }
                    FilterResults().apply {
                        values = filtered
                        count = filtered.size
                    }
                } ?: FilterResults()
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                filterResults?.takeIf { it.count > 0 }?.let {
                    setData(it.values as List<CustomMovie>)
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieImage: ImageView
        var genre: TextView

        init {
            movieImage = itemView.findViewById(R.id.movieImage)
            genre = itemView.findViewById(R.id.genre)

        }
    }
}