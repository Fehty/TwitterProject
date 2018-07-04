package com.fehtystudio.twitterproject

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_alert_dialog.*

class AlertDialogFragment : DialogFragment() {

    private val realm = Realm.getDefaultInstance()
    private val messagesRealmModel = MessagesRealmModel()
    private val listFragment = fragmentManager!!.findFragmentById(R.id.container) as ListFragment
    private val alertDialogEditTextFirstText = alertDialogEditTextFirst.text
    private var itemId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialogPositiveButton.setOnClickListener {

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

                messagesRealmModel.text = alertDialogEditTextFirstText.toString()
                realm.insertOrUpdate(messagesRealmModel)
            }

            listFragment.addItem(alertDialogEditTextFirstText.toString())

            this@AlertDialogFragment.dismiss()
            alertDialogEditTextFirstText.clear()
        }

        alertDialogNegativeButton.setOnClickListener {
            this.dismiss()
        }
    }
}