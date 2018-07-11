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
import com.fehtystudio.twitterproject.Activity.MyApplication
import com.fehtystudio.twitterproject.Adapter.RecyclerViewAdapter
import com.fehtystudio.twitterproject.DataClass.FirebaseDatabaseData
import com.fehtystudio.twitterproject.DataClass.ListData
import com.fehtystudio.twitterproject.DataClass.MessagesRealmModel
import com.fehtystudio.twitterproject.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private val messagesRealmModel = MessagesRealmModel()
    private val reference = FirebaseDatabase.getInstance().reference.child("User1").child("Messages")
    private val myApplication = MyApplication()

    private fun getDataFromFireBase() {
        reference.addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        dataSnapshot.children.forEach {
                            val value = it.getValue(String::class.java)
                            val valueId = it.key

                          realm.executeTransaction {
                              messagesRealmModel.id = valueId!!.toInt()
                              messagesRealmModel.text = value
                              realm.insertOrUpdate(messagesRealmModel)
                          }
                             // Log.e("*#*#*#*#*#**#", "$valueId")
                        }
                        initList()
                    }

                    override fun onCancelled(databaseError: DatabaseError) = Unit
                })
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
                //  Log.e("*#*#*#*#*#**#", "Id = ${it.id} Text = ${it.text}")
            }
        }
        adapter = RecyclerViewAdapter(this@ListFragment, list)
        recyclerView.adapter = adapter
        progressBar.visibility = View.GONE
    }

    fun addItem(itemText: String, itemId: Int) {
        list.add(ListData(itemText, itemId))
    }

    fun invokeAlertDialogFragmentMethodToSetValuesToEditText(id: Int) {
        val alertDialogFragment = AlertDialogFragment(true, id)
        val activity = context as FragmentActivity
        alertDialogFragment.show(activity.supportFragmentManager, "fragmentAlert")
    }

    private fun getRestApi() {
        myApplication.retrofit.getDataFromFireBaseDataBase().enqueue(object : Callback<FirebaseDatabaseData> {
            override fun onResponse(call: Call<FirebaseDatabaseData>?, response: Response<FirebaseDatabaseData>?) {
                val value = response!!.body()!!.messages
                realm.executeTransaction {
                    for (i in 1..value.size) {
                        messagesRealmModel.id = i
                        messagesRealmModel.text = value.getValue("ID$i").toString()
                        realm.insertOrUpdate(messagesRealmModel)
                    }
                }
                initList()
            }

            override fun onFailure(call: Call<FirebaseDatabaseData>?, t: Throwable?) {
                Log.e("**#*#*#*#*#**", "onFailure", t)
            }
        })
    }
}