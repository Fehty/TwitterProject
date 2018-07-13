package com.fehtystudio.twitterproject.Interface

import com.fehtystudio.twitterproject.DataClass.FireBaseDatabaseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/{userID}.json")
    fun getDataFromFireBaseDataBase(@Path("userID") userID: String): Call<FireBaseDatabaseData>
}

