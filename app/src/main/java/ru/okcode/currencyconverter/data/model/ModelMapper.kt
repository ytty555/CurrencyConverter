package ru.okcode.currencyconverter.data.model

interface ModelMapper<Entity, Model> {
    fun mapToModel(entity: Entity?): Model?
    fun mapToEntity(model: Model): Entity
}