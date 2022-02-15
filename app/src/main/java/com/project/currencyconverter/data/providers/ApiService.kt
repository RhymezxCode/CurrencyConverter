package com.project.currencyconverter.data.providers

import com.project.currencyconverter.data.providers.URL.BASE_URL
import retrofit2.Retrofit

object ApiService {

    fun apiCall(): ApiList = Retrofit.Builder()
        .baseUrl(BASE_URL)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(ApiWorker.gsonConverter)
        .client(ApiWorker.client)
        .build()
        .create(ApiList::class.java)



}