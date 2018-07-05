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
import com.google.firebase.database.FirebaseDatabase
import io.realm.Realm

class RecyclerViewAdapter(private val listFragment: ListFragment? = null,
                          private val list: MutableList<ListData> = mutableListOf())
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_template, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val delete = view.findViewById<TextView>(R.id.delete)
        private val edit = view.findViewById<TextView>(R.id.edit)
        private val itemText = view.findViewById<TextView>(R.id.itemText)
        private val realm = Realm.getDefaultInstance()

        fun bind(data: ListData) {
            itemText.text = data.itemText

            delete.setOnClickListener {
                realm.executeTransaction {
                    realm.where(MessagesRealmModel::class.java).equalTo("id", data.id).findFirst()!!.deleteFromRealm()
                }

                val database = FirebaseDatabase.getInstance().reference
                database.child(data.id.toString()).removeValue()

                removeItem(adapterPosition)
            }

            edit.setOnClickListener {
                listFragment!!.invokeAlertDialogFragmentMethodToSetValuesToEditText(data.id)
            }
        }

        private fun removeItem(position: Int) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }

    }
}


//        private fun initFireBaseData() {
//
//            val database = FirebaseDatabase.getInstance().reference
//
//            database.addChildEventListener(object : ChildEventListener {
//                override fun onCancelled(p0: DatabaseError) {}
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
//                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//                    val value = dataSnapshot.getValue(String::class.java)
//                    //     list.remove(ListData(value!!))
//                    removeItem(adapterPosition)
//                    adapter = RecyclerViewAdapter(activity, this@ListFragment, list)
//                    recyclerView.adapter = adapter
//                }
//
//                override fun onChildAdded(dataSnapshot: DataSnapshot, string: String?) {
//                    val value = dataSnapshot.getValue(String::class.java)
//                    list.add(ListData(value!!))
//                    adapter = RecyclerViewAdapter(activity, this@ListFragment, list)
//                    progressBar.visibility = View.GONE
//                    recyclerView.adapter = adapter
//                }
//            })
//        }






