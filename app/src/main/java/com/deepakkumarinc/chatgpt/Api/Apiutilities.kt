package com.deepakkumarinc.chatgpt.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Apiutilities {

    fun getApiInterface():ApiInterface{
        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}