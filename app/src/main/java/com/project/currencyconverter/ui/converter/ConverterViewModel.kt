package com.project.currencyconverter.ui.converter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.project.currencyconverter.data.model.converter.ConverterRequest
import com.project.currencyconverter.data.model.converter.ConverterResponse
import com.project.currencyconverter.data.repository.ConverterRepository
import com.project.currencyconverter.data.util.Event
import com.project.currencyconverter.data.util.Result
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class ConverterViewModel(
    app: Application,
    private val repo: ConverterRepository
) : AndroidViewModel(app) {

    private val _converterResponse =
        MutableLiveData<Event<Result<ConverterResponse>>>()
    val converterResponse: LiveData<Event<Result<ConverterResponse>>> =
        _converterResponse


    fun convertNow(
        ConverterRequest: ConverterRequest
    ) = viewModelScope.launch {
        convert(ConverterRequest)
    }

    private suspend fun convert(
        ConverterRequest: ConverterRequest
    ) {
        _converterResponse.postValue(Event(Result.Loading()))
        try {

            val response = repo.convertCurrency(ConverterRequest)
            _converterResponse.postValue(handleResponse(response))

        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _converterResponse.postValue(
                        Event(
                            Result.Error(
                                "Network Failure"
                            )
                        )
                    )
                }
                else -> {
                    _converterResponse.postValue(
                        Event(
                            Result.Error(
                                t.localizedMessage!!
                            )
                        )
                    )
                }
            }
            _converterResponse.postValue(
                Event(
                    Result.Error(
                        t.localizedMessage!!
                    )
                )
            )
        }
    }

    private fun handleResponse(response: Response<ConverterResponse>): Event<Result<ConverterResponse>> {
        if (response.isSuccessful) {
            if(response.code() == 200){
                response.body()?.let { resultResponse ->
                    return Event(Result.Success(resultResponse))
                }
            }else{
                response.body()?.let {
                    return Event(Result.Error("Error!!!"))
                }
            }
        }
//        val errorObject = JSONObject(response.errorBody()!!.string())
//        val message: String = errorObject.get("error").toString()
        return Event(Result.Error("Error!!!"))
    }
}