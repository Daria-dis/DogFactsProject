package com.dasha.dogfactsproject.data.internet

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {

    fun getInstance (): Retrofit {
        val mOkHttpClient = OkHttpClient.Builder().build()

        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://dog-api.kinduff.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()
        return retrofit
    }
}