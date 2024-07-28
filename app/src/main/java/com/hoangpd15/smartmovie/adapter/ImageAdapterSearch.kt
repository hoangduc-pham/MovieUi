package com.hoangpd15.smartmovie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hoangpd15.smartmovie.R

class ImageAdapterSearch(
    private val imageUrlList: List<String?>,
    private val nameMovie: List<String>,
    private val rate: List<Double>,
    private val idMovie: List<Int>,
    private val clickListener: (Int) -> Unit

) : RecyclerView.Adapter<ImageAdapterSearch.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val nameMovie: TextView = itemView.findViewById(R.id.nameMovie)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
    }

    inner class EventHandler(
        private val holder: ImageViewHolder,
        private val position: Int
    ) {

        fun bind() {
            setupClickListener()
            loadImage()
            setMovieDetails()
            setUpRatingbar()
        }

        private fun setupClickListener() {
            holder.itemView.setOnClickListener {
                clickListener(idMovie[position])
            }
        }

        private fun loadImage() {
            val imageUrl = "https://image.tmdb.org/t/p/w500" + imageUrlList[position]
            if (imageUrlList[position].isNullOrEmpty()) {
                Glide.with(holder.imageView.context)
                    .load(R.drawable.movie_empty)
                    .into(holder.imageView)
            } else {
                Glide.with(holder.imageView.context)
                    .load(imageUrl)
                    .into(holder.imageView)
            }
        }

        private fun setMovieDetails() {
            val nameMovie = nameMovie[position]
            if (nameMovie.length > 25) {
                holder.nameMovie.text = nameMovie.substring(0, 15) + "..."
            } else {
                holder.nameMovie.text = nameMovie
            }
        }

        private fun setUpRatingbar() {
            val rate = rate[position]
            holder.ratingBar.rating = (rate / 2).toFloat()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_container3, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val evenHandler = EventHandler(holder, position)
        evenHandler.bind()
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }
}
