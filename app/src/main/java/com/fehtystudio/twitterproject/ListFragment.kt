package com.fehtystudio.twitterproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListFragment : Fragment() {

    private val realm = Realm.getDefaultInstance()
    var adapter = RecyclerViewAdapter()
    var list = mutableListOf<ApiListResultData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    fun initList() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        //realm.executeTransaction {
        //    val realmLoop = realm.where(MessagesRealmModel::class.java).sort("id").findAll()
        //    realmLoop.forEach {
        //        list.add(ListData(it.text.toString(), it.id!!.toInt()))
        //    }
        //}

        loadListFromApi()

    }

    private fun loadListFromApi() {
        val retrofit = Retrofit
                .Builder()
                .baseUrl("https://restcountries.eu")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)

        retrofit.getList().enqueue(object : Callback<List<ApiListResultData>> {
            override fun onResponse(call: Call<List<ApiListResultData>>?, response: Response<List<ApiListResultData>>?) {
                Log.e("*#*#*#*#*#*", "onSuccess")
                adapter = RecyclerViewAdapter(activity, this@ListFragment, response!!.body()!!)

                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<List<ApiListResultData>>?, t: Throwable?) {
                Log.e("*#*#*#*#*#*", "onFailure")
            }
        })
    }

    fun addItem(itemText: String, itemId: Int) {
      //  list.add(ListData(itemText, itemId))
    }

    fun invokeAlertDialogFragmentMethodToSetValuesToEditText(id: Int) {
        val alertDialogFragment = AlertDialogFragment(true, id)
        val activity = context as FragmentActivity
        alertDialogFragment.show(activity.supportFragmentManager, "fragment_alert")
    }
}