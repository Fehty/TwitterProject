package com.fehtystudio.twitterproject.Activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.fehtystudio.twitterproject.Fragments.AlertDialogFragment
import com.fehtystudio.twitterproject.Fragments.ListFragment
import com.fehtystudio.twitterproject.Fragments.SignInProfileFragment
import com.fehtystudio.twitterproject.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val alertDialogActivity = AlertDialogFragment()
    private val signInProfileFragment = SignInProfileFragment()
    private val listFragment = ListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragmentTo(listFragment)

        floatingActionButton.setOnClickListener {
            alertDialogActivity.show(supportFragmentManager, "alertDialogFragment")
        }

        profileImage.setOnClickListener {
            when {
                !signInProfileFragment.isAdded -> {
                    setFragmentTo(signInProfileFragment)
                    floatingActionButton.hide()
                }
//                signInProfileFragment.isAdded -> Log.e("*#*#*#*#*#**#", "SignInProfileFragment")
            }
        }
    }

    private fun setFragmentTo(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, fragment)
                .commit()
    }
}


