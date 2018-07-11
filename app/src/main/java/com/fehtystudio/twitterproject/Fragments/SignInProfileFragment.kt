package com.fehtystudio.twitterproject.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fehtystudio.twitterproject.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in_profile.*

class SignInProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in_profile, container, false)
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            fragmentManager
                    ?.beginTransaction()
                    ?.addToBackStack(null)
                    ?.replace(R.id.container, SuccessRegistrationProfileFragment())
                    ?.commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInButton.setOnClickListener {
            when {

                login.text.toString().isNotEmpty() and password!!.text.toString().isNotEmpty() -> {
                    auth.signInWithEmailAndPassword(login.text.toString(), password!!.text.toString())
                            .addOnCompleteListener { task ->
                                when {
                                    task.isSuccessful -> {
                                        Log.e("*#*#*#*##*", "SignInSuccessfully")
                                        fragmentManager
                                                ?.beginTransaction()
                                                ?.addToBackStack(null)
                                                ?.replace(R.id.container, ListFragment())
                                                ?.commit()
                                    }
                                    else -> Toast.makeText(activity, "Sign In Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                }

                else -> Toast.makeText(activity, "Field is Empty", Toast.LENGTH_SHORT).show()
            }
        }

        registrationButton.setOnClickListener {
            fragmentManager
                    ?.beginTransaction()
                    ?.addToBackStack(null)
                    ?.replace(R.id.container, RegistrationProfileFragment())
                    ?.commit()
        }
    }
}