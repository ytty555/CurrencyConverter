package ru.okcode.currencyconverter

import android.content.Context
import ru.okcode.currencyconverter.model.DefaultRatesRepository
import ru.okcode.currencyconverter.model.RatesRepository
import ru.okcode.currencyconverter.model.api.ApiService
import ru.okcode.currencyconverter.model.api.DefaultApiService
import ru.okcode.currencyconverter.model.db.RatesDao
import ru.okcode.currencyconverter.model.db.RatesDatabase

class ServiceLocator {
    companion object {
        fun bindLocalDb(context: Context): RatesDatabase {
            return RatesDatabase.getInstance(context)
        }

        fun bindLocalDbDao(context: Context): RatesDao {
            return bindLocalDb(context).ratesDao
        }

        fun bindRemoteDataSource(): ApiService {
            return DefaultApiService()
        }

        fun bindRatesRepository(context: Context): RatesRepository {
            val remoteDataSource = bindRemoteDataSource()
            val localDataSource = bindLocalDbDao(context)
            return DefaultRatesRepository(remoteDataSource, localDataSource)
        }
    }
}