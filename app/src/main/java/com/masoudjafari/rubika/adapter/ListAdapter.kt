package com.masoudjafari.rubika.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.masoudjafari.rubika.dataClass.MovieList
import com.masoudjafari.rubika.databinding.ItemListBinding
import com.masoudjafari.rubika.util.RecyclerItemClickListener
import com.masoudjafari.rubika.util.SpeedyLinearLayoutManager
import java.util.*

class ListAdapter : RecyclerView.Adapter<ListAdapterViewHolder>() {
    private lateinit var listAdapterCallback: ListAdapterCallback
    private var list = mutableListOf<MovieList>()

    fun setData(data: List<MovieList>) {
        this.list = data.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater, parent, false)
        return ListAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAdapterViewHolder, position: Int) {
        val movieAdapter = MovieAdapter()

        val item = list[position]
        val moviesRecyclerView = holder.binding.recyclerView
        val mLayoutManager = SpeedyLinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, true)

        moviesRecyclerView.apply {
            layoutManager = mLayoutManager
            adapter = movieAdapter
        }

        movieAdapter.setData(item.movies, position)

        // change Items order and make different lists
        changeItemsOrder(position, mLayoutManager)

        // make smooth scroll
        makeSmoothScroll(holder.itemView.context, item.movies.size, moviesRecyclerView)

        moviesRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(
                holder.itemView.context,
                moviesRecyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        listAdapterCallback.onItemClick(holder.adapterPosition, position)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {}
                })
        )
    }

    interface ListAdapterCallback {
        fun onItemClick(rowPosition: Int, columnPosition: Int)
    }

    fun setOnItemClickListener(listAdapterCallback: ListAdapterCallback) {
        this.listAdapterCallback = listAdapterCallback
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun changeItemsOrder(position: Int, layoutManager: SpeedyLinearLayoutManager) {
        if (position % 2 != 0)
            layoutManager.scrollToPositionWithOffset(2, 150)
        else
            layoutManager.scrollToPositionWithOffset(0, 0)
    }

    private fun makeSmoothScroll(context: Context, listsize : Int, recyclerView: RecyclerView ) {
        val smoothScroller: SmoothScroller = object : LinearSmoothScroller(context) {
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return 5000f / displayMetrics.densityDpi
            }

            override fun calculateTimeForDeceleration(dx: Int): Int {
                return 0
            }
        }

        smoothScroller.targetPosition = listsize
        recyclerView.layoutManager?.startSmoothScroll(smoothScroller)

        // start smooth scroll again after user scroll
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    smoothScroller.targetPosition = listsize
                    Timer().schedule(object : TimerTask() {
                        override fun run() {
                            recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
                        }
                    }, 1000)
                }
            }
        })
    }
}

class ListAdapterViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)
