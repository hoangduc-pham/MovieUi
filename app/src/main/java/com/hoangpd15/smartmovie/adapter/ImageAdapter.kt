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
    inner class EventHandler(
        private val holder: ImageViewHolder,
        private val position: Int
    ) {

        fun bind() {
            setupClickListener()
            loadImage()
            setMovieDetails()
            setupFavoriteIcon()
        }
        private fun setupClickListener() {
            holder.itemView.setOnClickListener {
                clickListener(listId[position])
            }
        }

        private fun loadImage() {
            val imageUrl = "https://image.tmdb.org/t/p/w500" + imageUrlList[position]
            Glide.with(holder.imageView.context)
                .load(imageUrl)
                .into(holder.imageView)
        }

        private fun setMovieDetails() {
            val movieName = nameMovie[position]
            holder.nameMovie.text = if (movieName.length > 20 && isGridView) {
                movieName.substring(0, 15) + "..."
            } else {
                movieName
            }
            holder.voteCount.text = if (isGridView) {
                "Lượt xem: ${voteCount[position]}"
            } else {
                val overviewText = overView[position]
                if (overviewText.length < 100) overviewText else overviewText.substring(0, 95) + "..."
            }
        }

        private fun setupFavoriteIcon() {
            val isFavorite = runBlocking {
                AppDatabase.getDatabase(context).favoriteMovieDao().isFavoriteMovie(listId[position])
            }
            holder.favoriteIcon.setImageResource(
                if (isFavorite) R.drawable.ic_baseline_star_24
                else R.drawable.ic_baseline_star_border_24
            )
            holder.favoriteIcon.setOnClickListener {
                handleFavoriteClick(isFavorite)
            }
        }

        private fun handleFavoriteClick(isFavorite: Boolean) {
            if (isFavorite) {
                (context as MainActivity).lifecycleScope.launch {
                    AppDatabase.getDatabase(context).favoriteMovieDao().deleteById(listId[position])
                    holder.favoriteIcon.setImageResource(R.drawable.ic_baseline_star_border_24)
                }
            } else {
                val movie = FavoriteMovie(
                    id = listId[position],
                    title = nameMovie[position],
                    posterPath = "https://image.tmdb.org/t/p/w500" + imageUrlList[position],
                    voteCount = voteCount[position].toInt(),
                    overView = overView[position]
                )
                (context as MainActivity).lifecycleScope.launch {
                    AppDatabase.getDatabase(context).favoriteMovieDao().insert(movie)
                    holder.favoriteIcon.setImageResource(R.drawable.ic_baseline_star_24)
                }
            }
        }
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
        Log.d("ImageAdapter", "position: $position")
        val eventHandler = EventHandler(holder, position)
        eventHandler.bind()
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
//        notifyDataSetChanged()
    }
}
