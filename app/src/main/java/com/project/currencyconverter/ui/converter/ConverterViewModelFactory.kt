package com.project.currencyconverter.ui.converter

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.project.currencyconverter.data.repository.ConverterRepository


class ConverterViewModelFactory(
    val app: Application,
    private val repo: ConverterRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConverterViewModel::class.java)) {
            return ConverterViewModel(app, repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}