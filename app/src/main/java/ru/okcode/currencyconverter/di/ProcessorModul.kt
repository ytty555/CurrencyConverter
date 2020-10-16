package ru.okcode.currencyconverter.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
<<<<<<< HEAD
import ru.okcode.currencyconverter.model.processor.TextProcessor
import ru.okcode.currencyconverter.model.processor.TextProcessorImpl
=======
import ru.okcode.currencyconverter.ui.basechooser.TextProcessor
import ru.okcode.currencyconverter.ui.basechooser.TextProcessorImpl
>>>>>>> release/v2.0.1

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ProcessorModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindTextProcessor(impl: TextProcessorImpl): TextProcessor
}