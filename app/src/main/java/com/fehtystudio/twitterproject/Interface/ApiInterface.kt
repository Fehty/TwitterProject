package com.fehtystudio.twitterproject.Interface

import com.fehtystudio.twitterproject.DataClass.FirebaseDatabaseData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("/User1.json")
    fun getDataFromFireBaseDataBase(): Call<FirebaseDatabaseData>
}

