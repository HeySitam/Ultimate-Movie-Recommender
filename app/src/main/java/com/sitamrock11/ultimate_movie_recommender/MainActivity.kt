package com.sitamrock11.ultimate_movie_recommender

import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    val parentList = ArrayList<ParentItem>()

    // val childList = ArrayList<ChildItem>()
    val movieName = arrayOf("Batman", "Superman", "Avengers", "Ironman", "Thor", "Hulk")
    val okHttpClient = OkHttpClient()
    val base_Url = "https://www.omdbapi.com/?apikey=5bab5e39"
    lateinit var adapter:ParentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        rvParent.layoutManager = layoutManager
        for (i in movieName.indices) {
            getCall(movieName[i]).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val jsonObject = JSONObject(response.body!!.string())
                        val movies: JSONArray = jsonObject.getJSONArray("Search")
                        addToChildList(movies)
                        val parentItem = ParentItem(movieName[i], addToChildList(movies))
                        parentList.add(parentItem)
                        runOnUiThread {
                             adapter = ParentAdapter(parentList)
                              rvParent.adapter = adapter
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            })

        }

    }
        @Throws(JSONException::class)
        private fun addToChildList(movies: JSONArray): List<ChildItem> {
            val childList = ArrayList<ChildItem>()
            for (i in 0 until movies.length()) {
                val childItem = ChildItem()
                childItem.title = movies.getJSONObject(i).getString("Title")
                childItem.year = movies.getJSONObject(i).getString("Year")
                childItem.imdbID = movies.getJSONObject(i).getString("imdbID")
                childItem.type = movies.getJSONObject(i).getString("Type")
                childItem.poster = movies.getJSONObject(i).getString("Poster")
                childList.add(childItem)
            }
            return childList
        }

        private fun getCall(search_key: String): Call {
            val urlBuilder: HttpUrl.Builder = base_Url.toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addQueryParameter("s", search_key)
            val url = urlBuilder.build().toString()
            val request = Request.Builder().url(url).build()
            val mCall = okHttpClient.newCall(request)
            return mCall
        }
    }
