package ru.okcode.currencyconverter.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ru.okcode.currencyconverter.model.repositories.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepositoryMain(impl: RepositoryMainImpl): RepositoryMain

    @Binds
    @Singleton
    abstract fun bindRepositoryCache(impl: RepositoryCacheImpl): RepositoryCache

    @Binds
    @Singleton
    abstract fun bindRepositoryConfig(impl: RepositoryConfigImpl): RepositoryConfig
}