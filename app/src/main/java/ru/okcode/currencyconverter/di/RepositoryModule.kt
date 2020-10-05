package ru.okcode.currencyconverter.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.okcode.currencyconverter.data.db.ready.ReadyRates
import ru.okcode.currencyconverter.data.db.ready.ReadyRatesImpl
import ru.okcode.currencyconverter.data.repository.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepositoryCache(impl: CacheRepositoryImpl): CacheRepository

    @Binds
    @Singleton
    abstract fun bindRepositoryConfig(impl: ConfigRepositoryImpl): ConfigRepository

    @Binds
    @Singleton
    abstract fun bindRepositoryReady(impl: ReadyRepositoryImpl): ReadyRepository

    @Binds
    @Singleton
    abstract fun bindRepositoryNetwork(impl: NetworkRepositoryImpl): NetworkRepository

    @Binds
    @Singleton
    abstract fun bindRepositoryRaw(impl: RawRatesRepositoryImpl): RawRatesRepository

    @Binds
    @Singleton
    abstract fun bindReadyRates(impl: ReadyRatesImpl): ReadyRates
}