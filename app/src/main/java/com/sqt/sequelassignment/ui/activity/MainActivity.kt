package com.sqt.sequelassignment.ui.activity

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.google.gson.Gson
import com.sqt.sequelassignment.R
import com.sqt.sequelassignment.adapter.SearchAdapter
import com.sqt.sequelassignment.databinding.ActivityMainBinding
import com.sqt.sequelassignment.db.AppDatabase
import com.sqt.sequelassignment.model.SearchResultItem
import com.sqt.sequelassignment.model.SearchResultResponse
import com.sqt.sequelassignment.viewmodel.DashboardViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    var sampleJson = "{\n" +
        "  \"Search\": [\n" +
        "    {\n" +
        "      \"Title\": \"The Shawshank Redemption\",\n" +
        "      \"Year\": \"1994\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0111161\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"The Godfather\",\n" +
        "      \"Year\": \"1972\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0068646\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"The Dark Knight\",\n" +
        "      \"Year\": \"2008\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0468569\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"The Godfather Part II\",\n" +
        "      \"Year\": \"1974\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BMWMwMGQzZTItY2JlNC00OWZiLWIyMDctNDk2ZDQ2YjRjMWQ0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0071562\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"12 Angry Men\",\n" +
        "      \"Year\": \"1957\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BMWU4N2FjNzYtNTVkNC00NzQ0LTg0MjAtYTJlMjFhNGUxZDFmXkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0050083\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Schindler's List\",\n" +
        "      \"Year\": \"1993\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0108052\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"The Lord of the Rings: The Return of the King\",\n" +
        "      \"Year\": \"2003\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BNzA5ZDNlZWMtM2NhNS00NDJjLTk4NDItYTRmY2EwMWZlMTY3XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0167260\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Pulp Fiction\",\n" +
        "      \"Year\": \"1994\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0110912\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"The Lord of the Rings: The Fellowship of the Ring\",\n" +
        "      \"Year\": \"2001\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0120737\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"The Good, the Bad and the Ugly\",\n" +
        "      \"Year\": \"1966\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BNjJlYmNkZGItM2NhYy00MjlmLTk5NmQtNjg1NmM2ODU4OTMwXkEyXkFqcGdeQXVyMjUzOTY1NTc@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0060196\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Forrest Gump\",\n" +
        "      \"Year\": \"1994\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0109830\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Fight Club\",\n" +
        "      \"Year\": \"1999\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BNDIzNDU0YzEtYzE5Ni00ZjlkLTk5ZjgtNjM3NWE4YzA3Nzk3XkEyXkFqcGdeQXVyMjUzOTY1NTc@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0137523\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"The Lord of the Rings: The Two Towers\",\n" +
        "      \"Year\": \"2002\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BZGMxZTdjZmYtMmE2Ni00ZTdkLWI5NTgtNjlmMjBiNzU2MmI5XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0167261\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Inception\",\n" +
        "      \"Year\": \"2010\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt1375666\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Star Wars: Episode V - The Empire Strikes Back\",\n" +
        "      \"Year\": \"1980\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0080684\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"The Matrix\",\n" +
        "      \"Year\": \"1999\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0133093\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Goodfellas\",\n" +
        "      \"Year\": \"1990\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BY2NkZjEzMDgtN2RjYy00YzM1LWI4ZmQtMjIwYjFjNmI3ZGEwXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0099685\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"One Flew Over the Cuckoo's Nest\",\n" +
        "      \"Year\": \"1975\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BZjA0OWVhOTAtYWQxNi00YzNhLWI4ZjYtNjFjZTEyYjJlNDVlL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0073486\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Se7en\",\n" +
        "      \"Year\": \"1995\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BOTUwODM5MTctZjczMi00OTk4LTg3NWUtNmVhMTAzNTNjYjcyXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0114369\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Seven Samurai\",\n" +
        "      \"Year\": \"1954\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BNWQ3OTM4ZGItMWEwZi00MjI5LWI3YzgtNTYwNWRkNmIzMGI5XkEyXkFqcGdeQXVyNDY2MTk1ODk@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0047478\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"It's a Wonderful Life\",\n" +
        "      \"Year\": \"1946\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BZjc4NDZhZWMtNGEzYS00ZWU2LThlM2ItNTA0YzQ0OTExMTE2XkEyXkFqcGdeQXVyNjUwMzI2NzU@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0038650\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"The Silence of the Lambs\",\n" +
        "      \"Year\": \"1991\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BNjNhZTk0ZmEtNjJhMi00YzFlLWE1MmEtYzM1M2ZmMGMwMTU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0102926\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"City of God\",\n" +
        "      \"Year\": \"2002\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BOTMwYjc5ZmItYTFjZC00ZGQ3LTlkNTMtMjZiNTZlMWQzNzI5XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0317248\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Saving Private Ryan\",\n" +
        "      \"Year\": \"1998\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BZjhkMDM4MWItZTVjOC00ZDRhLThmYTAtM2I5NzBmNmNlMzI1XkEyXkFqcGdeQXVyNDYyMDk5MTU@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0120815\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Life Is Beautiful\",\n" +
        "      \"Year\": \"1997\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BYmJmM2Q4NmMtYThmNC00ZjRlLWEyZmItZTIwOTBlZDQ3NTQ1XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0118799\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Interstellar\",\n" +
        "      \"Year\": \"2014\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0816692\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"The Green Mile\",\n" +
        "      \"Year\": \"1999\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0120689\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Star Wars: Episode IV - A New Hope\",\n" +
        "      \"Year\": \"1977\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BOTA5NjhiOTAtZWM0ZC00MWNhLThiMzEtZDFkOTk2OTU1ZDJkXkEyXkFqcGdeQXVyMTA4NDI1NTQx._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0076759\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Terminator 2: Judgment Day\",\n" +
        "      \"Year\": \"1991\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BMGU2NzRmZjUtOGUxYS00ZjdjLWEwZWItY2NlM2JhNjkxNTFmXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0103064\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    },\n" +
        "    {\n" +
        "      \"Title\": \"Back to the Future\",\n" +
        "      \"Year\": \"1985\",\n" +
        "      \"Poster\": \"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BZmU0M2Y1OGUtZjIxNi00ZjBkLTg1MjgtOWIyNThiZWIwYjRiXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg\",\n" +
        "      \"imdbID\": \"tt0088763\",\n" +
        "      \"Type\": \"movie\"\n" +
        "    }\n" +
        "  ]\n" +
        "}"
    val sample = Gson().fromJson(sampleJson, SearchResultResponse::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.statusBarColor = Color.TRANSPARENT
        WindowCompat.setDecorFitsSystemWindows(window, false)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        activityMainBinding.text = "XZ"
        activityMainBinding.recyclerResult.layoutManager =
            GridLayoutManager(this@MainActivity, 3)

        setSampleResponse()

        activityMainBinding.editQuery.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (count > 3) {
                    getSearchDetails(s.toString())
                } else {
                    setSampleResponse()
                }
            }
        })

        activityMainBinding.imageViewHistory.setOnClickListener(
            View.OnClickListener {
                val i = Intent(this@MainActivity, HistoryActivity::class.java)
                startActivity(i)
            }
        )
    }

    private fun setSampleResponse() {
        activityMainBinding.recyclerResult.visibility = View.VISIBLE
        activityMainBinding.textEmpty.visibility = View.GONE

        val adapter = SearchAdapter(sample.Search, this@MainActivity)
        activityMainBinding.recyclerResult.adapter = adapter
    }

    private fun getSearchDetails(query: String) {
//        activityMainBinding.progressBar.visibility = View.VISIBLE
        val movieDetailViewModel: DashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]
        movieDetailViewModel.getSearchDetails(query).observe(
            this
        ) { apiResponse ->
//            activityMainBinding.progressBar.visibility = View.GONE

            if (apiResponse == null) {
                Log.d(ContentValues.TAG, "onChanged: Handle Error")
                return@observe
            }
            if (apiResponse.error == null) {
                val result: SearchResultResponse = apiResponse.posts!!
                if (result.Response == "True") {
                    activityMainBinding.recyclerResult.visibility = View.VISIBLE
                    activityMainBinding.textEmpty.visibility = View.GONE

                    val adapter = SearchAdapter(result.Search, this@MainActivity)
                    activityMainBinding.recyclerResult.adapter = adapter
                } else {
                    activityMainBinding.recyclerResult.visibility = View.GONE
                    activityMainBinding.textEmpty.visibility = View.VISIBLE
                }
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

    fun setClickItem(searchResultItem: SearchResultItem) {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "sequel_DB"
        ).allowMainThreadQueries().build()
        var databaseList = ArrayList<SearchResultItem>()

        databaseList = db.historydao().getAll() as ArrayList<SearchResultItem>
        if (!databaseList.contains(searchResultItem)) {
            db.historydao().insertAll(searchResultItem)
        }
        val i = Intent(this@MainActivity, MovieDetailActivity::class.java)
        i.putExtra("imdbID", searchResultItem.imdbID)
        startActivity(i)
    }
}
