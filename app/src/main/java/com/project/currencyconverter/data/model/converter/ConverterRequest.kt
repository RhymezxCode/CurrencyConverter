package com.project.currencyconverter.data.model.converter

import com.google.gson.annotations.SerializedName

data class ConverterRequest(
    @SerializedName("from") var from: String,
    @SerializedName("to") var to: String,
    @SerializedName("amount") var Int: String
)