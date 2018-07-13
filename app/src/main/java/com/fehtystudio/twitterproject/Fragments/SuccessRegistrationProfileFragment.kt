package com.fehtystudio.twitterproject.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fehtystudio.twitterproject.DataClass.AuthRealmModel
import com.fehtystudio.twitterproject.R
import com.google.firebase.auth.FirebaseAuth
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_success_registration_profile.*

class SuccessRegistrationProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_success_registration_profile, container, false)
    }

    private val user = FirebaseAuth.getInstance()
    private val realm = Realm.getDefaultInstance()
    private val mainUserPassword = realm.where(AuthRealmModel::class.java).findFirst()!!.userPassword

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.floatingActionButton.hide()
        userEmail.setText(user.currentUser!!.email)
        userPassword.setText(mainUserPassword)

        saveUserData.setOnClickListener {
            user.currentUser!!.updateEmail(userEmail.text.toString())
            user.currentUser!!.updatePassword(userPassword.text.toString())
            realm.executeTransaction {
                val result = realm.where(AuthRealmModel::class.java).equalTo("userPassword", mainUserPassword).findFirst()
                result!!.userPassword = userPassword.text.toString()
            }
            Toast.makeText(activity, "You have changed your data", Toast.LENGTH_SHORT).show()
        }

        backButton.setOnClickListener {
            goToListFragment()
        }

        signOutButton.setOnClickListener {
            realm.executeTransaction {
                realm.where(AuthRealmModel::class.java).findAll().deleteAllFromRealm()
            }
            user.signOut()
            Toast.makeText(activity, "You have signed out", Toast.LENGTH_SHORT).show()
            goToListFragment()
        }
    }

    private fun goToListFragment() {
        fragmentManager
                ?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.container, ListFragment())
                ?.commit()
    }
}