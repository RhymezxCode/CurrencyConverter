package com.project.currencyconverter.data.repository

import com.project.currencyconverter.data.model.converter.ConverterRequest
import com.project.currencyconverter.data.providers.ApiService
import com.project.currencyconverter.data.providers.URL
import retrofit2.http.Body

class ConverterRepository {

    suspend fun convertCurrency(
        @Body converterRequest: ConverterRequest
    ) = ApiService.apiCall().convert(URL.KEY, converterRequest)

}