package com.jicsoftwarestudio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jicsoftwarestudio.model.dataLocal.AppDatabase
import com.jicsoftwarestudio.model.FavoriteMovie
import com.jicsoftwarestudio.movie_ass.MainActivity
import com.jicsoftwarestudio.movie_ass.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ImageAdapter(
    private val imageUrlList: List<String>,
    private val listId: List<Int>,
    private val nameMovie: List<String>,
    private val voteCount: List<String>,
    private var isVertical: Boolean,
    private val context: Context,
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val nameMovie: TextView = itemView.findViewById(R.id.nameMovie)
        val voteCount: TextView = itemView.findViewById(R.id.voteCount)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = if (isVertical) {
            LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
        } else  {
            LayoutInflater.from(parent.context).inflate(R.layout.image_container2, parent, false)
        }
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = "https://image.tmdb.org/t/p/w500" + imageUrlList[position]
        Glide.with(holder.imageView.context)
            .load(imageUrl)
            .into(holder.imageView)
        val nameMovie = nameMovie[position]
        if (nameMovie.length > 20 && !isVertical) {
            holder.nameMovie.text = nameMovie.substring(0, 15) + "..."
        } else {
            holder.nameMovie.text = nameMovie
        }
        val voteCount = voteCount[position]
        holder.voteCount.text = "Lượt xem: " + voteCount
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
                    poster_path = imageUrl,
                    vote_count = voteCount.toInt()
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
