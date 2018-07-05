package com.fehtystudio.twitterproject.Interface

import com.fehtystudio.twitterproject.DataClass.ApiListResultData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("/rest/v2/all")
    fun getList(): Call<List<ApiListResultData>>
}