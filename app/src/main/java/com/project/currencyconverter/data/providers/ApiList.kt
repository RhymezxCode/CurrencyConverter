package com.project.currencyconverter.data.providers

import com.project.currencyconverter.data.model.converter.ConverterRequest
import com.project.currencyconverter.data.model.converter.ConverterResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiList {

    @POST("latest/convert")
    suspend fun convert(
        @Query("access_key") key: String,
        @Body converterRequest: ConverterRequest
    ): Response<ConverterResponse> // body data


}

