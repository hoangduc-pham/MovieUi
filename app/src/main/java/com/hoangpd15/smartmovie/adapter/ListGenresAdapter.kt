package com.hoangpd15.smartmovie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hoangpd15.smartmovie.R

class ListGenresAdapter(
    private val nameGenres: List<String>,
    private val idGenres: List<Int>,
    private val clickListener: (Pair<Int, String>) -> Unit

) : RecyclerView.Adapter<ListGenresAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val nameGenres: TextView = itemView.findViewById(R.id.nameGenres)
    }
    inner class EventHandler(
        private val holder: ImageViewHolder,
        private val position: Int
    ) {

        fun bind() {
            setupClickListener()
            loadImage()
            setMovieDetails()
        }
        private fun setupClickListener() {
            val idGenre = idGenres[position]
            val nameGenres = nameGenres[position]
            holder.itemView.setOnClickListener {
                clickListener(Pair(idGenre,nameGenres) )
            }
        }

        private fun loadImage() {
            val imageUrl = "https://image.tmdb.org/t/p/w500/wTW2t8ocWDlHns8I7vQxuqkyK58.jpg"
            Glide.with(holder.imageView.context)
                .load(imageUrl)
                .into(holder.imageView)
        }

        private fun setMovieDetails() {
            val nameGenres = nameGenres[position]
            holder.nameGenres.text = nameGenres
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_container4, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val evenHandler = EventHandler(holder,position)
        evenHandler.bind()
    }

    override fun getItemCount(): Int {
        return nameGenres.size
    }
}