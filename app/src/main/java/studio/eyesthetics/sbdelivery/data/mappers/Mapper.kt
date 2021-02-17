package studio.eyesthetics.sbdelivery.data.mappers

interface Mapper<E, D> {
    fun mapFromEntity(type: E): D
    fun mapFromListEntity(type: List<E>): List<D>
}