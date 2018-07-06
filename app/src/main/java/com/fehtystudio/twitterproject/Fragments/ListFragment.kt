package com.fehtystudio.twitterproject.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fehtystudio.twitterproject.Adapter.RecyclerViewAdapter
import com.fehtystudio.twitterproject.DataClass.ListData
import com.fehtystudio.twitterproject.DataClass.MessagesRealmModel
import com.fehtystudio.twitterproject.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_recycler_view.*

class ListFragment : Fragment() {

    internal val list = mutableListOf<ListData>()
    private val realm = Realm.getDefaultInstance()
    private var adapter = RecyclerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromFireBase()
    }

    fun initList(initDecor: Boolean = true) {
        if (initDecor) {
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
        realm.executeTransaction {
            val realmLoop = realm.where(MessagesRealmModel::class.java).sort("id").findAll()
            realmLoop.forEach {
                list.add(ListData(it.text.toString(), it.id!!.toInt()))
                Log.e("*#*#*#*#*#**#", "Id = ${it.id} Text = ${it.text}")
            }
        }
        adapter = RecyclerViewAdapter(this@ListFragment, list)
        recyclerView.adapter = adapter
    }

    private val messagesRealmModel = MessagesRealmModel()
    private val reference = FirebaseDatabase.getInstance().reference.child("Messages")
    private val result = realm.where(MessagesRealmModel::class.java).max("id")
    private var itemId = 0

    private fun getDataFromFireBase() {
        reference.addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dataSnapshot.children.forEach {
                            val value = it.getValue(String::class.java)

                            realm.executeTransaction {
                                when (result) {
                                    null -> itemId = 1
                                    else -> itemId = result.toInt() + 1
                                }
                                messagesRealmModel.id = itemId
                                messagesRealmModel.text = value
                                realm.insertOrUpdate(messagesRealmModel)
                            }
                        }
                        initList()
                    }

                    override fun onCancelled(databaseError: DatabaseError) = Unit
                })
    }

    fun addItem(itemText: String, itemId: Int) {
        list.add(ListData(itemText, itemId))
    }

    fun invokeAlertDialogFragmentMethodToSetValuesToEditText(id: Int) {
        val alertDialogFragment = AlertDialogFragment(true, id)
        val activity = context as FragmentActivity
        alertDialogFragment.show(activity.supportFragmentManager, "fragmentAlert")
    }
}


//        var childEventListener = (object : ChildEventListener {
//            override fun onCancelled(p0: DatabaseError) {}
//            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
//            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//                val value = dataSnapshot.getValue(String::class.java)
//                list.remove(ListData(value!!))
//                adapter = RecyclerViewAdapter(this@ListFragment, list)
//                recyclerView.adapter = adapter
//            }
//
//            override fun onChildAdded(dataSnapshot: DataSnapshot, string: String?) {
////               list.add(ListData(value))
//              //  adapter = RecyclerViewAdapter(this@ListFragment, list)
//              //  recyclerView.adapter = adapter
////                Log.e("*#*#*#*#*#**#", "ItemText = $value")
////                val value = dataSnapshot.getValue(String::class.java)
////                Log.e("*#*#*#*#*#**#", "ItemText = $value")
//                val value = dataSnapshot.value
//            }
//        })

//
//    var valueEventListener = object: ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            var str = ""
//            for (postSnapshot in dataSnapshot.children) {
//                str = postSnapshot.value.toString()
//            }
//            Log.e("*#*#*#*#*#**#", "ItemAdded = ${str}")
//        }
//
//        override fun onCancelled(p0: DatabaseError) {
//
//        }
//
//    }


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
