package com.mock_project.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jicsoftwarestudio.movie_ass.R
import com.mock_project.model.dataLocal.AppDatabase
import com.mock_project.model.FavoriteMovie
import com.mock_project.MainActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ImageAdapter(
    private val imageUrlList: List<String>,
    private val listId: List<Int>,
    private val nameMovie: List<String>,
    private val voteCount: List<String>,
    private val overView: List<String>,
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
}
