package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import studio.eyesthetics.sbdelivery.workers.SyncWorker

class SplashViewModel : ViewModel() {

    private val currentWork = WorkManager.getInstance().getWorkInfosByTagLiveData(SYNC_WORK_TAG)

    init {
        val syncWorker = OneTimeWorkRequestBuilder<SyncWorker>().addTag(SYNC_WORK_TAG).build()
        WorkManager.getInstance().enqueueUniqueWork(SYNC_WORK_TAG, ExistingWorkPolicy.KEEP, syncWorker)
    }

    fun observeCurrentWork(owner: LifecycleOwner, onChange: (List<WorkInfo>) -> Unit) {
        currentWork.observe(owner, Observer { onChange(it) })
    }

    companion object {
        const val SYNC_WORK_TAG = "SYNC_WORK_TAG"
    }
}