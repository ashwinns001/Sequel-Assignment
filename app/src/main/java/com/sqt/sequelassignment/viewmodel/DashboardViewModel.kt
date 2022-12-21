package com.sqt.sequelassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sqt.sequelassignment.retrofit.RetrofitClient
import com.sqt.sequelassignment.model.ApiResponse
import com.sqt.sequelassignment.model.MovieDetailResponse
import com.sqt.sequelassignment.model.SearchApiResponse
import com.sqt.sequelassignment.model.SearchResultResponse
import com.sqt.sequelassignment.repository.MovieDetailRepository
import com.sqt.sequelassignment.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {
    var retrofitClient: RetrofitClient = RetrofitClient()



    fun getSearchDetails(query: String): MutableLiveData<SearchApiResponse> {
        val apiResponse = MutableLiveData<SearchApiResponse>()
        val apiService = retrofitClient.getRetrofitClient()!!.create(ApiService::class.java)
        val call: Call<SearchResultResponse> = apiService.getSearch(query, "45a4d852")
        call.enqueue(object : Callback<SearchResultResponse> {
            override fun onFailure(call: Call<SearchResultResponse>?, t: Throwable?) {
                apiResponse.postValue(SearchApiResponse(t!!))
            }

            override fun onResponse(
                call: Call<SearchResultResponse>?,
                response: Response<SearchResultResponse>?
            ) {
                if (response!!.isSuccessful) {
                    return apiResponse.postValue(SearchApiResponse(response.body()!!))
//
                }
            }

        })

        return apiResponse
    }


}