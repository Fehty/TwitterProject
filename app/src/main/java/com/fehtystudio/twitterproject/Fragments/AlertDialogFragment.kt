package com.fehtystudio.twitterproject.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fehtystudio.twitterproject.DataClass.MessagesRealmModel
import com.fehtystudio.twitterproject.R
import com.google.firebase.database.FirebaseDatabase
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_alert_dialog.*

@SuppressLint("ValidFragment")
class AlertDialogFragment(private val setValues: Boolean = false,
                          private val enteredId: Int? = null)
    : DialogFragment() {

    private val realm = Realm.getDefaultInstance()
    private val messagesRealmModel = MessagesRealmModel()
    private var itemId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val realmResult = realm.where(MessagesRealmModel::class.java).equalTo("id", enteredId).findFirst()
        val fireBaseDataBase = FirebaseDatabase.getInstance().reference
        if (setValues) alertDialogEditTextFirst.setText(realmResult!!.text)

        alertDialogPositiveButton.setOnClickListener {

            when {

                setValues -> {
                    realm.executeTransaction {
                        realmResult!!.text = alertDialogEditTextFirst.text.toString()
                    }
                    val listFragment = fragmentManager!!.findFragmentById(R.id.container) as ListFragment
                    listFragment.list.clear()
                    listFragment.initList(false)
                    fireBaseDataBase.child("Messages").child(enteredId.toString()).setValue(alertDialogEditTextFirst.text.toString())
                }

                else -> {
                    val alertDialogEditTextFirstText = alertDialogEditTextFirst.text.toString()
                    val listFragment = fragmentManager!!.findFragmentById(R.id.container) as ListFragment
                    realm.executeTransaction {
                        val result = realm.where(MessagesRealmModel::class.java).max("id")
                        when (result) {
                            null -> itemId = 1
                            else -> itemId = result.toInt() + 1
                        }
                        messagesRealmModel.id = itemId
                        messagesRealmModel.text = alertDialogEditTextFirstText
                        realm.insertOrUpdate(messagesRealmModel)

                        fireBaseDataBase.child("Messages").child(itemId.toString()).setValue(alertDialogEditTextFirst.text.toString())
                    }
                    listFragment.addItem(alertDialogEditTextFirstText, itemId)
                }

            }
            this@AlertDialogFragment.dismiss()
        }

        alertDialogNegativeButton.setOnClickListener {
            this.dismiss()
            realm.executeTransaction {
                val realmLoop = realm.where(MessagesRealmModel::class.java).sort("id").findAll()
                realmLoop.forEach {
                    Log.e("*#*#*#*#*#**#", "ItemText = ${it.text}      ID = ${it.id}")
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        alertDialogEditTextFirst.text.clear()
    }
}