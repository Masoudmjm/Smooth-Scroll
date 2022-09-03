package com.masoudjafari.SmoothScroll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.masoudjafari.SmoothScroll.adapter.ListAdapter
import com.masoudjafari.SmoothScroll.dataClass.Movie
import com.masoudjafari.SmoothScroll.dataClass.MovieList
import com.masoudjafari.SmoothScroll.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var listAdapter = ListAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setList()
    }

    private fun setList() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
        }
        listAdapter.setData(fakeList())

        listAdapter.setOnItemClickListener(object : ListAdapter.ListAdapterCallback {
            override fun onItemClick(rowPosition: Int, columnPosition: Int) {
                Toast.makeText(applicationContext, "row = $rowPosition  column = $columnPosition", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun fakeList() : List<MovieList> {
        val movie = Movie(1, "movie")
        val movieList = mutableListOf(movie)
        for (i in 1..20) {
            movieList.add(movie)
        }
        val movieList1 = MovieList(movieList)
        return listOf(movieList1, movieList1, movieList1).toMutableList()
    }
}