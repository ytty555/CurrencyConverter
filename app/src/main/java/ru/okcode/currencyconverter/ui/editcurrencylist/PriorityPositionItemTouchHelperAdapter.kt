package ru.okcode.currencyconverter.ui.editcurrencylist

interface PriorityPositionItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)
}