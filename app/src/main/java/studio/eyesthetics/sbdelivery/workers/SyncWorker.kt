package studio.eyesthetics.sbdelivery.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.data.repositories.categories.ICategoryRepository
import javax.inject.Inject

class SyncWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var categoryRepository: ICategoryRepository

    init {
        App.INSTANCE.appComponent.inject(this)
    }

    override suspend fun doWork(): Result {
        return try {
            categoryRepository.loadsCategoriesFromNetwork(0, 10)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}