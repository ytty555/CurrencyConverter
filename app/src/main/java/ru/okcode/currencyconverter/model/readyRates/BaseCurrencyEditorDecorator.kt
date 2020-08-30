package ru.okcode.currencyconverter.model.readyRates

class BaseCurrencyEditorDecorator(source: ReadyRates): RatesDecorator(source) {
    override fun writeRates(rates: Rates) {
        super.writeRates(rates)
    }
}