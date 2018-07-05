package com.fehtystudio.twitterproject.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fehtystudio.twitterproject.Adapter.RecyclerViewAdapter
import com.fehtystudio.twitterproject.DataClass.ListData
import com.fehtystudio.twitterproject.R
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_recycler_view.*

class ListFragment : Fragment() {

    private val realm = Realm.getDefaultInstance()
    var adapter = RecyclerViewAdapter()
    var list = mutableListOf<ListData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initFireBaseList()
    }

    fun initList() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
//        realm.executeTransaction {
//            val realmLoop = realm.where(MessagesRealmModel::class.java).sort("id").findAll()
//            realmLoop.forEach {
//                list.add(ListData(it.text.toString(), it.id!!.toInt()))
//            }
//        }
//        adapter = RecyclerViewAdapter(this@ListFragment, list)
        recyclerView.adapter = adapter
    }

    fun addItem(itemText: String, itemId: Int) {
        list.add(ListData(itemText, itemId))
    }

    fun invokeAlertDialogFragmentMethodToSetValuesToEditText(id: Int) {
        val alertDialogFragment = AlertDialogFragment(true, id)
        val activity = context as FragmentActivity
        alertDialogFragment.show(activity.supportFragmentManager, "fragmentAlert")
    }

    private fun initFireBaseList() {
        val database = FirebaseDatabase.getInstance().reference
        database.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                list.remove(ListData(value!!))
                adapter = RecyclerViewAdapter(this@ListFragment, list)
                recyclerView.adapter = adapter
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, string: String?) {
                val value = dataSnapshot.getValue(String::class.java)
                progressBar.visibility = View.GONE
                list.add(ListData(value!!))
                adapter = RecyclerViewAdapter(this@ListFragment, list)
                recyclerView.adapter = adapter
            }
        })
    }
}


//private fun loadListFromApi() {
//    val retrofit = Retrofit
//            .Builder()
//            .baseUrl("https://restcountries.eu")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiInterface::class.java)
//
//    retrofit.getList().enqueue(object : Callback<List<ApiListResultData>> {
//        override fun onResponse(call: Call<List<ApiListResultData>>?, response: Response<List<ApiListResultData>>?) {
//            Log.e("*#*#*#*#*#*", "onSuccess")
//            adapter = RecyclerViewAdapter(activity, this@ListFragment, response!!.body()!!)
//
//            recyclerView.adapter = adapter
//        }
//
//        override fun onFailure(call: Call<List<ApiListResultData>>?, t: Throwable?) {
//            Log.e("*#*#*#*#*#*", "onFailure", t)
//        }
//    })
//}
