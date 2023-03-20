package com.example.examplecleanarch.network

import com.google.gson.JsonElement
import com.step.examplechatgpt.model.GetResponseModel
import com.step.examplechatgpt.model.ReqestReponseModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @Headers("Content-Type: application/json")
    @POST
    fun postWithTokenCall(
        @Header("Authorization") token: String,
        @Url url: String,
        @Body params: ReqestReponseModel
    ): Call<GetResponseModel>


}