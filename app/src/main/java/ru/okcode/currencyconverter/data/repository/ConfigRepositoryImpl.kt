package ru.okcode.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.db.config.ConfigDao
import ru.okcode.currencyconverter.data.db.config.ConfigEntity
import ru.okcode.currencyconverter.data.db.config.ConfigMapper
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val configDao: ConfigDao,
    private val configMapper: ConfigMapper
) : ConfigRepository {

    override fun getConfigSingle(): Single<Config> {
        TODO("Not yet implemented")
    }
}