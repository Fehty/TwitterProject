package com.fehtystudio.twitterproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

     fun initList() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        realm.executeTransaction {
            val realmLoop = realm.where(MessagesRealmModel::class.java).sort("id").findAll()
            realmLoop.forEach {
                list.add(ListData(it.text.toString(), it.id!!.toInt()))
            }
        }

        adapter = RecyclerViewAdapter(activity, this, list)

        recyclerView.adapter = adapter
    }

    fun addItem(itemText: String, itemId: Int) {
        list.add(ListData(itemText, itemId))
    }

    fun invokeAlertDialogFragmentMethodToSetValuesToEditText(id: Int) {
        val alertDialogFragment = AlertDialogFragment(true, id)
        val activity = context as FragmentActivity
        alertDialogFragment.show(activity.supportFragmentManager, "fragment_alert")
    }
}