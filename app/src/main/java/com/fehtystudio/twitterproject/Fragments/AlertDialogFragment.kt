package com.fehtystudio.twitterproject.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fehtystudio.twitterproject.DataClass.MessagesRealmModel
import com.fehtystudio.twitterproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_alert_dialog.*

@SuppressLint("ValidFragment")
class AlertDialogFragment(private val setValues: Boolean = false,
                          private val enteredId: Int? = null)
    : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert_dialog, container, false)
    }

    private val realm = Realm.getDefaultInstance()
    private val messagesRealmModel = MessagesRealmModel()
    private val realmResult = realm.where(MessagesRealmModel::class.java).equalTo("id", enteredId).findFirst()
    private val fireBaseDataBase = FirebaseDatabase.getInstance().reference
    private val user = FirebaseAuth.getInstance().currentUser
    private val result = realm.where(MessagesRealmModel::class.java).max("id")
    private var itemId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listFragment = fragmentManager!!.findFragmentById(R.id.container) as ListFragment

        if (setValues) alertDialogEditTextFirst.setText(realmResult!!.text)
        alertDialogEditTextFirst.setSelection(alertDialogEditTextFirst.text.length)

        alertDialogPositiveButton.setOnClickListener {

            val alertDialogEditTextFirstText = alertDialogEditTextFirst.text.toString()

            when {

                setValues -> {
                    realm.executeTransaction {
                        realmResult!!.text = alertDialogEditTextFirst.text.toString()
                    }
                    listFragment.list.clear()
                    listFragment.initList(false)
                    fireBaseDataBase.child(user!!.uid).child("Messages").child("$enteredId").setValue(alertDialogEditTextFirst.text.toString())
                }

                user != null && alertDialogEditTextFirst.text.isNotEmpty() -> {
                    realm.executeTransaction {
                        when (result) {
                            null -> itemId = 1
                            else -> itemId = result.toInt() + 1
                        }
                        messagesRealmModel.id = itemId
                        messagesRealmModel.text = alertDialogEditTextFirstText
                        realm.insertOrUpdate(messagesRealmModel)

                    }
                    fireBaseDataBase
                            .child(user.uid)
                            .child("Messages")
                            .child("$itemId")
                            .setValue(alertDialogEditTextFirst.text.toString())

                    listFragment.addItem(alertDialogEditTextFirstText, itemId)
                }

                alertDialogEditTextFirst.text.isEmpty() -> Toast.makeText(activity, "Field is Empty", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(activity, "Log in", Toast.LENGTH_SHORT).show()
            }
            this@AlertDialogFragment.dismiss()
        }

        alertDialogNegativeButton.setOnClickListener {
            this.dismiss()
        }
    }

    override fun onPause() {
        super.onPause()
        alertDialogEditTextFirst.text.clear()
    }
}