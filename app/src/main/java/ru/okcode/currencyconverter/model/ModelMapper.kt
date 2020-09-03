package ru.okcode.currencyconverter.model

interface ModelMapper<Entity, Model> {
    fun toModel(entity: Entity): Model

    fun toEntity(model: Model): Entity
}