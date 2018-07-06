package com.fehtystudio.twitterproject.DataClass

import com.google.gson.annotations.SerializedName

data class ApiListResultData(
        @SerializedName("name") var name: String,
        @SerializedName("topLevelDomain") var topLevelDomain: List<String>,
        @SerializedName("alpha2Code") var alpha2Code: String,
        @SerializedName("alpha3Code") var alpha3Code: String,
        @SerializedName("callingCodes") var callingCodes: List<String>,
        @SerializedName("capital") var capital: String,
        @SerializedName("altSpellings") var altSpellings: List<String>,
        @SerializedName("region") var region: String
)
