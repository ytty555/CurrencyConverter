package ru.okcode.currencyconverter.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import ru.okcode.currencyconverter.ui.basechooser.TextProcessor
import ru.okcode.currencyconverter.ui.basechooser.TextProcessorImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ProcessorModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindTextProcessor(impl: TextProcessorImpl): TextProcessor
}