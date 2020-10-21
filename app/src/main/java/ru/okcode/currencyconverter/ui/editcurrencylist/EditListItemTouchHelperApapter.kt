package ru.okcode.currencyconverter.ui.editcurrencylist

interface EditListItemTouchHelperApapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)
}