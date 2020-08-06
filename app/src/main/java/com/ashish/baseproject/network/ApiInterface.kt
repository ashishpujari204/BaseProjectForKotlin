package com.ashish.baseproject.network


import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("api/v1/employees")
    fun getDataAsync(): Deferred<Response<JsonObject>>

}