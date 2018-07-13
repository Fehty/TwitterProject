package com.fehtystudio.twitterproject.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fehtystudio.twitterproject.DataClass.ListData
import com.fehtystudio.twitterproject.DataClass.MessagesRealmModel
import com.fehtystudio.twitterproject.Fragments.ListFragment
import com.fehtystudio.twitterproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.realm.Realm

class RecyclerViewAdapter(private val listFragment: ListFragment? = null,
                          private val list: MutableList<ListData>? = null)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_template, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val delete = view.findViewById<TextView>(R.id.swipeMenuDelete)
        private val edit = view.findViewById<TextView>(R.id.swipeMenuEdit)
        private val itemText = view.findViewById<TextView>(R.id.itemText)
        private val realm = Realm.getDefaultInstance()
        private val user = FirebaseAuth.getInstance()
        private val database = FirebaseDatabase.getInstance().reference

        fun bind(data: ListData) {
            itemText.text = data.itemTextRealmIo

            delete.setOnClickListener {

                realm.executeTransaction {
                    realm.where(MessagesRealmModel::class.java).equalTo("id", data.id).findFirst()!!.deleteFromRealm()
                }
                database.child(user.uid!!).child("Messages").child("${data.id}").removeValue()
                removeItem(adapterPosition)
            }

            edit.setOnClickListener {
                listFragment!!.invokeAlertDialogFragmentMethodToSetValuesToEditText(data.id!!)
            }
        }

        private fun removeItem(position: Int) {
            list!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}



