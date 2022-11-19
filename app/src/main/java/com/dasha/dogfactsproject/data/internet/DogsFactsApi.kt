package com.dasha.dogfactsproject.data.internet

import com.dasha.dogfactsproject.data.model.Facts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface DogsFactsApi {
    @GET("api/facts")
    suspend fun getOneFact() : Response<Facts>

    @GET("api/facts")
    suspend fun getSomeFacts(@Query(value = "number") number:Int) : Response<Facts>

}