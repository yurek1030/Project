package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.project.models.Movie
import com.example.project.models.MovieResponse
import com.example.project.services.MovieApiInterface
import com.example.project.services.MovieApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_movies_list.layoutManager = LinearLayoutManager(this)
        rv_movies_list.setHasFixedSize(true)


        swipeRefreshLayout = findViewById(R.id.swipeToRefresh)

        getMovieData { movies : List<Movie> ->
            rv_movies_list.adapter = MovieAdapter(movies)
        }

        swipeRefreshLayout.setOnRefreshListener{
            getMovieData { movies : List<Movie> ->
                rv_movies_list.adapter = MovieAdapter(movies)
            }
            Toast.makeText(this, "Page refreshed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getMovieData(callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(swipeRefreshLayout.isRefreshing){
                    swipeRefreshLayout.isRefreshing = false
                }
                return callback(response.body()!!.movies)
            }

        })
    }

}
