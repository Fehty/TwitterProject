package com.fehtystudio.twitterproject

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/rest/v2/all")
    fun getList() : Call<List<ApiListResultData>>
}