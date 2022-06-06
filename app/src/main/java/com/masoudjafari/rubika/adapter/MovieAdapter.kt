package com.masoudjafari.rubika.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.masoudjafari.rubika.R
import com.masoudjafari.rubika.dataClass.Movie
import com.masoudjafari.rubika.databinding.ItemMovieBinding

class MovieAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var list = mutableListOf<Movie>()
    private var row = 0

    fun setData(mainModels: List<Movie>, row: Int) {
        this.row = row
        this.list = mainModels.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        when (row) {
            0 -> {
                when {
                    position == 0 -> holder.binding.ivMovie.setImageResource(R.drawable.img1)
                    position == 1 -> holder.binding.ivMovie.setImageResource(R.drawable.img4)
                    position == 2 -> holder.binding.ivMovie.setImageResource(R.drawable.img3)
                    position %2 == 0 -> holder.binding.ivMovie.setImageResource(R.drawable.img2)
                    else -> holder.binding.ivMovie.setImageResource(R.drawable.img1)
                }
            }
            1 -> {
                when {
                    position == 0 -> holder.binding.ivMovie.setImageResource(R.drawable.img3)
                    position == 1 -> holder.binding.ivMovie.setImageResource(R.drawable.img1)
                    position == 2 -> holder.binding.ivMovie.setImageResource(R.drawable.img2)
                    position %2 == 0 -> holder.binding.ivMovie.setImageResource(R.drawable.img4)
                    else -> holder.binding.ivMovie.setImageResource(R.drawable.img3)
                }
            }
            2 -> {
                when {
                    position == 0 -> holder.binding.ivMovie.setImageResource(R.drawable.img2)
                    position == 1 -> holder.binding.ivMovie.setImageResource(R.drawable.img3)
                    position == 2 -> holder.binding.ivMovie.setImageResource(R.drawable.img4)
                    position %2 == 0 -> holder.binding.ivMovie.setImageResource(R.drawable.img1)
                    else -> holder.binding.ivMovie.setImageResource(R.drawable.img2)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)
