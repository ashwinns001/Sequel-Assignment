package com.sqt.sequelassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sqt.sequelassignment.model.ApiResponse
import com.sqt.sequelassignment.model.MovieDetailResponse
import com.sqt.sequelassignment.retrofit.ApiService
import com.sqt.sequelassignment.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel : ViewModel() {

    var retrofitClient: RetrofitClient = RetrofitClient()

    fun getMoviesDetails(imdbId: String): MutableLiveData<ApiResponse> {
        val apiResponse = MutableLiveData<ApiResponse>()
        val apiService = retrofitClient.getRetrofitClient()!!.create(ApiService::class.java)
        val call: Call<MovieDetailResponse> = apiService.getMovie(imdbId, "45a4d852")
        call.enqueue(object : Callback<MovieDetailResponse> {
            override fun onFailure(call: Call<MovieDetailResponse>?, t: Throwable?) {
                apiResponse.postValue(ApiResponse(t!!))
            }

            override fun onResponse(
                call: Call<MovieDetailResponse>?,
                response: Response<MovieDetailResponse>?
            ) {
                if (response!!.isSuccessful) {
                    return apiResponse.postValue(ApiResponse(response.body()!!))
//
                }
            }
        })

        return apiResponse
    }
    fun String.capitalizeFirstCharacter(): String {
        return substring(0, 1).toUpperCase() + substring(1)
    }
}
