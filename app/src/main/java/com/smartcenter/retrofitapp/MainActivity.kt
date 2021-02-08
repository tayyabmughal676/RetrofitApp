package com.smartcenter.retrofitapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartcenter.retrofitapp.Models.User
import com.smartcenter.retrofitapp.Network.ApiClient
import com.smartcenter.retrofitapp.Utility.hideProgressBar
import com.smartcenter.retrofitapp.Utility.isInternetAvailable
import com.smartcenter.retrofitapp.Utility.showProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView

    private var listUsers: MutableList<User> = mutableListOf<User>()
    private var adapter: UsersAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listUsers = mutableListOf()

        mRecyclerView = findViewById(R.id.recyclerView)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UsersAdapter(
            this,
            listUsers
        )
        mRecyclerView.adapter = adapter

        if (isInternetAvailable()) {
            getUsersData()
        }

    }

    private fun getUsersData() {
        showProgressBar()
        ApiClient.apiService.getUsers().enqueue(object : Callback<MutableList<User>> {
            override fun onResponse(
                call: Call<MutableList<User>>,
                response: Response<MutableList<User>>
            ) {
                hideProgressBar()
                val usersResponse = response.body()
                listUsers.clear()
                usersResponse?.let {
                    listUsers.addAll(it)
                }
                adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                hideProgressBar()
                Log.e("error", t.localizedMessage)
            }
        })
    }
}