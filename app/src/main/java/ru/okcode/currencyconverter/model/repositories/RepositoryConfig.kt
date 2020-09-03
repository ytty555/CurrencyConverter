package ru.okcode.currencyconverter.model.repositories

import androidx.lifecycle.LiveData

interface RepositoryConfig {
    val baseCurrencyCode: LiveData<String>
}