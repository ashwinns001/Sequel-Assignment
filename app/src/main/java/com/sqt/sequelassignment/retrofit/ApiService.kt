package com.sqt.sequelassignment.retrofit

import com.sqt.sequelassignment.model.MovieDetailResponse
import com.sqt.sequelassignment.model.SearchResultResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    fun getMovie(@Query("i")movie_title: String, @Query("apikey")api_key: String): Call<MovieDetailResponse>
    @GET("/")
    fun getSearch(@Query("s")query: String, @Query("apikey")api_key: String): Call<SearchResultResponse>

}