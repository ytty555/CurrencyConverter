package ru.okcode.currencyconverter.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.okcode.currencyconverter.model.repositories.*
import ru.okcode.currencyconverter.util.TextProcessor
import ru.okcode.currencyconverter.util.TextProcessorImpl
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
    abstract fun bindTextProcessor(impl: TextProcessorImpl): TextProcessor
}