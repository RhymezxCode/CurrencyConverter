package com.project.currencyconverter.data.model.converter

data class ConverterResponse(
    val success: Boolean,
    val query: Query,
    val error: ErrorData,
    val info: Info,
    val historical: String,
    val date: String,
    val result: Double
)