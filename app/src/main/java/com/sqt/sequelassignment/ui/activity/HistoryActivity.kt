package com.sqt.sequelassignment.ui.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.sqt.sequelassignment.R
import com.sqt.sequelassignment.adapter.HistoryAdapter
import com.sqt.sequelassignment.adapter.SearchAdapter
import com.sqt.sequelassignment.databinding.ActivityHistoryBinding
import com.sqt.sequelassignment.databinding.ActivityMainBinding
import com.sqt.sequelassignment.db.AppDatabase
import com.sqt.sequelassignment.model.SearchResultItem

class HistoryActivity : AppCompatActivity() {
    private lateinit var activityHistoryBinding: ActivityHistoryBinding
    var databaseList = ArrayList<SearchResultItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.statusBarColor = Color.BLACK
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        activityHistoryBinding=DataBindingUtil.setContentView(this,R.layout.activity_history)


        setSupportActionBar(activityHistoryBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        activityHistoryBinding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        supportActionBar!!.title = "History"
        activityHistoryBinding.toolbar.setNavigationOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        activityHistoryBinding.recclerViewHistory.layoutManager =
            GridLayoutManager(this@HistoryActivity, 3)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "sequel_DB"
        ).allowMainThreadQueries().build()

        databaseList = db.historydao().getAll() as ArrayList<SearchResultItem>
if (databaseList.size==0){
    activityHistoryBinding.textEmpty.visibility = View.VISIBLE
}
        else
    activityHistoryBinding.textEmpty.visibility = View.GONE

        val adapter = HistoryAdapter(databaseList, this@HistoryActivity)
        activityHistoryBinding.recclerViewHistory.adapter = adapter

    }
     fun setOnClick(searchResultItem: SearchResultItem){
         val i = Intent(this@HistoryActivity, MovieDetailActivity::class.java)
         i.putExtra("imdbID", searchResultItem.imdbID)
         startActivity(i)
    }
}