package com.fehtystudio.twitterproject.DataClass

import io.realm.RealmObject

open class MessagesRealmModel :  RealmObject() {
    var id: Int? = null
    var text: String? = null
}