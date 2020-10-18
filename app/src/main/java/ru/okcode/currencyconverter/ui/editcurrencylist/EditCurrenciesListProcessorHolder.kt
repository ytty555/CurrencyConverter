package ru.okcode.currencyconverter.ui.editcurrencylist

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import ru.okcode.currencyconverter.data.model.Config
import ru.okcode.currencyconverter.data.model.ConfiguredCurrency
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListAction.*
import ru.okcode.currencyconverter.ui.editcurrencylist.EditCurrenciesListResult.*
import javax.inject.Inject

class EditCurrenciesListProcessorHolder @Inject constructor() {
    internal val actionProcessor:
            ObservableTransformer<EditCurrenciesListAction, EditCurrenciesListResult> =
        ObservableTransformer { actions ->
            actions.publish { shared ->
                Observable.merge(
                    shared.ofType(AddCurrencyAction::class.java)
                        .compose(addCurrencyProcessor),
                    shared.ofType(RemoveCurrencyAction::class.java)
                        .compose(removeCurrencyProcessor),
                    shared.ofType(MoveCurrencyAction::class.java)
                        .compose(moveCurrencyProcessor)
                )
                    .mergeWith(
                        shared.filter { action ->
                            action !is AddCurrencyAction
                                    && action !is RemoveCurrencyAction
                                    && action !is MoveCurrencyAction
                        }.flatMap { action ->
                            Observable.error(
                                IllegalArgumentException("Unknown Action type: $action")
                            )
                        }
                    )
            }
        }

    private val addCurrencyProcessor:
            ObservableTransformer<AddCurrencyAction, AddCurrencyResult> =
        TODO()

    private val moveCurrencyProcessor:
            ObservableTransformer<MoveCurrencyAction, MoveCurrencyResult> =
        TODO()

    private val removeCurrencyProcessor:
            ObservableTransformer<RemoveCurrencyAction, RemoveCurrencyResult> =
        TODO()

    internal fun getConfiguredCurrencyList(config: Config) : Observable<List<ConfiguredCurrency>> {
        TODO()
    }
}