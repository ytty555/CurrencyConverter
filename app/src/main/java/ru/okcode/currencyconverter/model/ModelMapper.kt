package ru.okcode.currencyconverter.model

interface ModelMapper<Entity, Model> {
    fun mapToModel(entity: Entity): Model
    fun mapToEntity(model: Model): Entity
}