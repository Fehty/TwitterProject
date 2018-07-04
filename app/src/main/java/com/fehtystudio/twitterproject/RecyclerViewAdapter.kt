package com.fehtystudio.twitterproject

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.Realm


class RecyclerViewAdapter(private val context: Context? = null,
                          private val listFragment: ListFragment? = null,
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
                    removeItem(adapterPosition)
                }
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