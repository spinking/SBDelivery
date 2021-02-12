package studio.eyesthetics.sbdelivery.data.mappers

import studio.eyesthetics.sbdelivery.data.database.entities.CategoryEntity
import studio.eyesthetics.sbdelivery.data.models.categories.Category

class CategoryToCategoryEntityMapper : Mapper<Category, CategoryEntity> {
    override fun mapFromEntity(type: Category): CategoryEntity {
        return CategoryEntity(
            id = type.categoryId,
            name = type.name,
            order = type.order,
            icon = type.icon ?: "",
            parent = type.parent ?: "",
            active = type.active,
            createdAt = type.createdAt,
            updatedAt = type.updatedAt
        )
    }

    override fun mapFromListEntity(type: List<Category>): List<CategoryEntity> {
        return type.map { mapFromEntity(it) }
    }
}