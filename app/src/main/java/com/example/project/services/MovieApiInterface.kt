package com.example.project.services

import com.example.project.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieApiInterface {

    @GET("/3/movie/popular?api_key=1b262cfecd88503c9f76ab883c11cfa5")
    fun getMovieList(): Call<MovieResponse>

}