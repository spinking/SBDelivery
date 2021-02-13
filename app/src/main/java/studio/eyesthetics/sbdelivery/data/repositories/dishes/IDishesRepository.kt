package studio.eyesthetics.sbdelivery.data.repositories.dishes

interface IDishesRepository {
    suspend fun loadDishesFromNetwork(offset: Int, limit: Int)
}