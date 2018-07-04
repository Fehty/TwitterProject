package com.fehtystudio.twitterproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_recycler_view.*

class ListFragment : Fragment() {

    private val realm = Realm.getDefaultInstance()
    private var adapter = RecyclerViewAdapter()
    private var list = mutableListOf<ListData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        realm.executeTransaction {
            val realmLoop = realm.where(MessagesRealmModel::class.java).sort("id").findAll()
            realmLoop.forEach {
                list.add(ListData(it.text.toString()))
            }
        }

        adapter = RecyclerViewAdapter(list)

        recyclerView.adapter = adapter
    }

    fun addItem(itemText : String) {
        list.add(ListData(itemText))
    }
}