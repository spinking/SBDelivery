package studio.eyesthetics.sbdelivery.data.repositories.categories

interface ICategoryRepository {
    suspend fun loadsCategoriesFromNetwork(offset: Int, limit: Int): Int
}