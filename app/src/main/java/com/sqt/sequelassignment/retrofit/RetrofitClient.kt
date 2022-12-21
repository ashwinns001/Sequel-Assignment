package com.sqt.sequelassignment.retrofit

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val baseURL = "http://www.omdbapi.com"
    private var retrofit : Retrofit? = null



    fun getRetrofitClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}