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
import kotlinx.android.synthetic.main.fragment_registration_profile.*

class RegistrationProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration_profile, container, false)
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.floatingActionButton.hide()
        backButton.setOnClickListener {
            changeThisFragmentTo(SignInProfileFragment())
        }

        registrationButton.setOnClickListener {
            when {

                email.text.toString().isNotEmpty() and password.text.toString().isNotEmpty() -> {
                    auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                            .addOnCompleteListener { task ->
                                when {
                                    task.isSuccessful -> {
                                        Toast.makeText(activity, "Registration completed", Toast.LENGTH_SHORT).show()
                                        changeThisFragmentTo(ListFragment())
                                    }
                                    else -> Toast.makeText(activity, "Registration failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                }
                else -> Toast.makeText(activity, "Field is Empty", Toast.LENGTH_SHORT).show()
            }
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