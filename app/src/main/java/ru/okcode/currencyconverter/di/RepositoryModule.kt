package ru.okcode.currencyconverter.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.okcode.currencyconverter.model.RepositoryMain
import ru.okcode.currencyconverter.model.RepositoryCache
import ru.okcode.currencyconverter.model.RepositoryCacheImpl
import ru.okcode.currencyconverter.model.RepositoryMainImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(impl: RepositoryMainImpl): RepositoryMain

    @Binds
    @Singleton
    abstract fun bindRepositoryCache(impl: RepositoryCacheImpl): RepositoryCache
}