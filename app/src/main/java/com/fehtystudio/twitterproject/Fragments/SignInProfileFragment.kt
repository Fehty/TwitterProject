package com.fehtystudio.twitterproject.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fehtystudio.twitterproject.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_in_profile.*

class SignInProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in_profile, container, false)
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) changeThisFragmentTo(SuccessRegistrationProfileFragment())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.floatingActionButton.hide()

        signInButton.setOnClickListener {
            when {

                email.text.toString().isNotEmpty() and password!!.text.toString().isNotEmpty() -> {
                    auth.signInWithEmailAndPassword(email.text.toString(), password!!.text.toString())
                            .addOnCompleteListener { task ->
                                when {
                                    task.isSuccessful -> changeThisFragmentTo(ListFragment())
                                    else -> Toast.makeText(activity, "Sign In Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                }

                else -> Toast.makeText(activity, "Field is Empty", Toast.LENGTH_SHORT).show()
            }
        }

        registrationButton.setOnClickListener {
            changeThisFragmentTo(RegistrationProfileFragment())
        }

        backButton.setOnClickListener {
            changeThisFragmentTo(ListFragment())
        }
    }

    private fun changeThisFragmentTo(fragment: Fragment) {
        fragmentManager
                ?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.container, fragment)
                ?.commit()
    }

    override fun onPause() {
        super.onPause()
        email.text.clear()
        password.text.clear()
    }
}