package studio.eyesthetics.sbdelivery.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.data.repositories.categories.ICategoryRepository
import studio.eyesthetics.sbdelivery.data.repositories.dishes.IDishesRepository
import javax.inject.Inject

class SyncWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var categoryRepository: ICategoryRepository

    @Inject
    lateinit var dishesRepository: IDishesRepository

    init {
        App.INSTANCE.appComponent.inject(this)
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val categoryResponse = async { categoryRepository.loadsCategoriesFromNetwork(0, 10) }
                val dishesResponse = async { dishesRepository.loadDishesFromNetwork(0, 10) }
                awaitAll(categoryResponse, dishesResponse)
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}