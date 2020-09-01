package ru.okcode.currencyconverter.model

import androidx.lifecycle.LiveData

interface RepositoryConfig {
    val baseCurrencyCode: LiveData<String>
}