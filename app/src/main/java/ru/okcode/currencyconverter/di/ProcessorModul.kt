package ru.okcode.currencyconverter.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import ru.okcode.currencyconverter.model.processor.TextProcessor
import ru.okcode.currencyconverter.model.processor.TextProcessorImpl

@Module
@InstallIn(FragmentComponent::class)
abstract class ProcessorModule {

    @Binds
    @FragmentScoped
    abstract fun bindTextProcessor(impl: TextProcessorImpl): TextProcessor
}