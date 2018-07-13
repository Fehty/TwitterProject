package com.fehtystudio.twitterproject.DataClass

import io.realm.RealmObject

open class AuthRealmModel : RealmObject() {
    var userPassword: String? = null
}