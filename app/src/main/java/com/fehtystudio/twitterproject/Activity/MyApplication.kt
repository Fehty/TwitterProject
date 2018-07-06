package com.fehtystudio.twitterproject.Activity

import android.app.Application
import io.realm.Realm

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
//        Realm.getDefaultInstance().executeTransaction {
//            Realm
//                    .getDefaultInstance()
//                    .where(MessagesRealmModel::class.java)
//                    .findAll()
//                    .deleteAllFromRealm()
//        }
//        FirebaseDatabase.getInstance().reference.removeValue()