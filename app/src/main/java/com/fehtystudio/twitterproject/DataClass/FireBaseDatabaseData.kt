package com.fehtystudio.twitterproject.DataClass

import com.google.gson.annotations.SerializedName

data class FireBaseDatabaseData(@SerializedName("Messages")
                                var messages: HashMap<Any, Any>)