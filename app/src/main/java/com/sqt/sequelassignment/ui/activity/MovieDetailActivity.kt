package com.sqt.sequelassignment.ui.activity

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqt.sequelassignment.R
import com.sqt.sequelassignment.adapter.GenereAdapter
import com.sqt.sequelassignment.databinding.ActivityMovieDetailBinding
import com.sqt.sequelassignment.model.MovieDetailResponse
import com.sqt.sequelassignment.retrofit.CheckConnection
import com.sqt.sequelassignment.viewmodel.MovieDetailViewModel
import com.squareup.picasso.Picasso


class MovieDetailActivity : AppCompatActivity() {
    private lateinit var activityMovieDetailBinding: ActivityMovieDetailBinding
    private val checkConnection by lazy { CheckConnection(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        //make status bar transparent
        window?.statusBarColor = Color.TRANSPARENT
        WindowCompat.setDecorFitsSystemWindows(window, false)

        activityMovieDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
// getting the recyclerview by its id

        // this creates a vertical layout Manager
        activityMovieDetailBinding.recyclerGenere.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("imdbID")
            //The key argument here must match that used in the other activity
            value?.let { getMovieDetails(it) }

        }
        activityMovieDetailBinding.imageViewBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    private fun getMovieDetails(query: String) {
        activityMovieDetailBinding.progressBar.visibility = View.VISIBLE
        val movieDetailViewModel: MovieDetailViewModel =
            ViewModelProvider(this)[MovieDetailViewModel::class.java]

        movieDetailViewModel.getMoviesDetails(query).observe(
            this
        ) { apiResponse ->
            activityMovieDetailBinding.progressBar.visibility = View.GONE
            if (apiResponse == null) {
                Log.d(ContentValues.TAG, "onChanged: Handle Error")
                return@observe
            }
            if (apiResponse.error == null) {
                activityMovieDetailBinding.constraintLayout.visibility=View.VISIBLE

                val result: MovieDetailResponse = apiResponse.posts!!
                activityMovieDetailBinding.response = apiResponse.posts
                val genereArray: List<String> = apiResponse.posts!!.Genre.split(",")
                val adapter = GenereAdapter(genereArray)
                activityMovieDetailBinding.recyclerGenere.adapter = adapter
                Picasso.get().load(
                    result.Poster
                ).into(activityMovieDetailBinding.imageViewTop)


            } else {
                val throwable: Throwable = apiResponse.error!!
                Toast.makeText(
                    this,
                    "Error is " + throwable.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}