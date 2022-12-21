package com.sqt.sequelassignment.repository

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.sqt.sequelassignment.model.ApiResponse
import com.sqt.sequelassignment.model.MovieDetailResponse
import com.sqt.sequelassignment.retrofit.ApiService
import com.sqt.sequelassignment.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailRepository : BaseObservable() {
    var retrofitClient: RetrofitClient = RetrofitClient()

    fun getMoviesDetails(imdbId: String): MutableLiveData <ApiResponse> {
        val apiResponse = MutableLiveData<ApiResponse>()
        val apiService = retrofitClient.getRetrofitClient()!!.create(ApiService::class.java)
        val call: Call<MovieDetailResponse> = apiService.getMovie(imdbId, "45a4d852")
        call.enqueue(object : Callback<MovieDetailResponse> {
            override fun onFailure(call: Call<MovieDetailResponse>?, t: Throwable?) {
                apiResponse.postValue(ApiResponse(t!!))
            }

            override fun onResponse(call: Call<MovieDetailResponse>?, response: Response<MovieDetailResponse>?) {
                if (response!!.isSuccessful) {
                    apiResponse.postValue(ApiResponse(response.body()!!))
                }
            }
        })

        return apiResponse
    }
}
