package com.fehtystudio.twitterproject

import io.realm.RealmObject

open class MessagesRealmModel : RealmObject() {
    var id: Int? = null
    var text: String? = null
    var dateTimeWhenWasAdded: String? = null
}