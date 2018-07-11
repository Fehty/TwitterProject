package com.fehtystudio.twitterproject.Activity

import android.app.Application
import com.fehtystudio.twitterproject.DataClass.MessagesRealmModel
import com.fehtystudio.twitterproject.Interface.ApiInterface
import io.realm.Realm
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val realm = Realm.getDefaultInstance()

        realm.executeTransaction {
            realm
                    .where(MessagesRealmModel::class.java)
                    .findAll()
                    .deleteAllFromRealm()
        }
    }

    val retrofit = Retrofit
            .Builder()
            .baseUrl("https://twitterproject-28a22.firebaseio.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)!!
}
