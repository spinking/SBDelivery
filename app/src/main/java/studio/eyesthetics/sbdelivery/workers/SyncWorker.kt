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
import studio.eyesthetics.sbdelivery.data.storage.Pref
import javax.inject.Inject

class SyncWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var categoryRepository: ICategoryRepository

    @Inject
    lateinit var dishesRepository: IDishesRepository

    @Inject
    lateinit var pref: Pref

    init {
        App.INSTANCE.appComponent.inject(this)
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val categoryResponse = async { categoryRepository.loadsCategoriesFromNetwork(0, 10) }
                val dishesResponse = async { dishesRepository.loadDishesFromNetwork(0, 10) }
                //maybe need get this value from auth repository, and don't use live data
                if (pref.isAuthLive.value == true) {
                    //TODO do profile, basket, orders and favorite dishes requests if is auth
                }
                awaitAll(categoryResponse, dishesResponse)
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}