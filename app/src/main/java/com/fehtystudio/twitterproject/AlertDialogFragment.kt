package com.fehtystudio.twitterproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_alert_dialog.*

@SuppressLint("ValidFragment")
class AlertDialogFragment(private var setValues: Boolean = false, private var enteredId: Int = 0) : DialogFragment() {

    private val realm = Realm.getDefaultInstance()
    private val messagesRealmModel = MessagesRealmModel()
    private val realmResult = realm.where(MessagesRealmModel::class.java).equalTo("id", enteredId).findFirst()
    private var itemId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialogPositiveButton.setOnClickListener {

            if (setValues) {
                realm.executeTransaction {
                    realmResult!!.text = alertDialogEditTextFirst.text.toString()
                }
                val listFragment = fragmentManager!!.findFragmentById(R.id.container) as ListFragment
                listFragment.list.clear()
                listFragment.initList()

                this.dismiss()
            } else {

                val alertDialogEditTextFirstText = alertDialogEditTextFirst.text.toString()

                val listFragment = fragmentManager!!.findFragmentById(R.id.container) as ListFragment

                realm.executeTransaction {
                    val result = realm.where(MessagesRealmModel::class.java).max("id")

                    when (result) {
                        null -> {
                            itemId = 1
                            messagesRealmModel.id = itemId
                        }
                        else -> {
                            itemId = result.toInt() + 1
                            messagesRealmModel.id = itemId
                        }
                    }

                    messagesRealmModel.text = alertDialogEditTextFirstText
                    realm.insertOrUpdate(messagesRealmModel)

                }

                listFragment.addItem(alertDialogEditTextFirstText, itemId)

                this@AlertDialogFragment.dismiss()
            }
        }

        if (setValues) {
            alertDialogEditTextFirst.setText(realmResult!!.text)
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