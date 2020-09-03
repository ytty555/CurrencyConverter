package ru.okcode.currencyconverter.model

interface ModelMapper<in Entity, out Model> {
    fun toModel(entity: Entity): Model
}