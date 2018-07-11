package com.fehtystudio.twitterproject.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fehtystudio.twitterproject.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_success_registration_profile.*

class SuccessRegistrationProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_success_registration_profile, container, false)
    }

    private val user = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userEmail.text = user.currentUser!!.email

        backButton.setOnClickListener {
            goToListFragment()
        }

        signOutButton.setOnClickListener {
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