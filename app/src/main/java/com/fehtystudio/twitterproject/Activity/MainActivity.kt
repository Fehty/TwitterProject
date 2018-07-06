package com.fehtystudio.twitterproject.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fehtystudio.twitterproject.Fragments.AlertDialogFragment
import com.fehtystudio.twitterproject.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val alertDialogActivity = AlertDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        floatingActionButton.setOnClickListener {
            alertDialogActivity.show(supportFragmentManager, "alertDialogFragment")
        }
    }
}
