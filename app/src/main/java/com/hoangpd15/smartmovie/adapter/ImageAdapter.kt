package com.hoangpd15.smartmovie.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hoangpd15.smartmovie.R
import com.hoangpd15.smartmovie.model.dataLocal.AppDatabase
import com.hoangpd15.smartmovie.model.FavoriteMovie
import com.hoangpd15.smartmovie.ui.MainActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ImageAdapter(
    private var imageUrlList: List<String>,
    private var listId: List<Int>,
    private var nameMovie: List<String>,
    private var voteCount: List<String>,
    private var overView: List<String>,
    private var isGridView: Boolean,
    private val context: Context,
    private val clickListener:(Int) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val nameMovie: TextView = itemView.findViewById(R.id.nameMovie)
        val voteCount: TextView = itemView.findViewById(R.id.voteCount)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = if (isGridView) {
            LayoutInflater.from(parent.context).inflate(R.layout.image_container2, parent, false)
        } else  {
            LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
        }
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Log.e("MovieDetailViewModel", "Error fetching movie details")
        val idMovie = listId[position]
        holder.itemView.setOnClickListener {
            clickListener(idMovie)
        }
        val imageUrl = "https://image.tmdb.org/t/p/w500" + imageUrlList[position]
        Glide.with(holder.imageView.context)
            .load(imageUrl)
            .into(holder.imageView)
        val nameMovie = nameMovie[position]
        if (nameMovie.length > 20 && isGridView) {
            holder.nameMovie.text = nameMovie.substring(0, 15) + "..."
        } else {
            holder.nameMovie.text = nameMovie
        }
        val voteCount = voteCount[position]
        val overView = overView[position]
        if(isGridView) {
            holder.voteCount.text = "Lượt xem: " + voteCount
        }else{
            holder.voteCount.text = if(overView.length<100) overView else overView.substring(0, 95) + "..."
        }
        // Check favoriteMovie
        var isFavorite = false
        runBlocking {
            isFavorite = AppDatabase.getDatabase(context).favoriteMovieDao().isFavoriteMovie(listId[position])
        }
        if (isFavorite) {
            holder.favoriteIcon.setImageResource(R.drawable.ic_baseline_star_24)
        } else {
            holder.favoriteIcon.setImageResource(R.drawable.ic_baseline_star_border_24)
        }

        holder.favoriteIcon.setOnClickListener {
            if (isFavorite) {
                (context as MainActivity).lifecycleScope.launch {
                    AppDatabase.getDatabase(context).favoriteMovieDao().deleteById(listId[position])
                    holder.favoriteIcon.setImageResource(R.drawable.ic_baseline_star_border_24)
                    isFavorite = false
                    return@launch
                }
            } else {
                // Save Room
                val movie = FavoriteMovie(
                    id = listId[position],
                    title = nameMovie,
                    posterPath = imageUrl,
                    voteCount = voteCount.toInt(),
                    overView = overView
                )
                (context as MainActivity).lifecycleScope.launch {
                    AppDatabase.getDatabase(context).favoriteMovieDao().insert(movie)
                    holder.favoriteIcon.setImageResource(R.drawable.ic_baseline_star_24)
                    isFavorite = true
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }
    fun updateData(newImageUrlList: List<String>, newIdList: List<Int>, newNameMovieList: List<String>, newVoteCountList: List<String>, newOverView: List<String>) {
        imageUrlList = newImageUrlList
        listId = newIdList
        nameMovie = newNameMovieList
        voteCount = newVoteCountList
        overView = newOverView
        notifyDataSetChanged()
    }
}
