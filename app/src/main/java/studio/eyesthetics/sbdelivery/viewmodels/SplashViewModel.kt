package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.workers.SyncWorker
import javax.inject.Inject

class SplashViewModel(
    handle: SavedStateHandle
) : BaseViewModel<SplashState>(handle, SplashState()) {

    init {
        val syncWorker = OneTimeWorkRequestBuilder<SyncWorker>().addTag(SYNC_WORK_TAG).build()
        WorkManager.getInstance().enqueueUniqueWork(SYNC_WORK_TAG, ExistingWorkPolicy.KEEP, syncWorker)
    }

    companion object {
        const val SYNC_WORK_TAG = "SYNC_WORK_TAG"
    }
}

class SplashViewModelFactory @Inject constructor(
) : IViewModelFactory<SplashViewModel> {
    override fun create(handle: SavedStateHandle): SplashViewModel {
        return SplashViewModel(handle)
    }
}

class SplashState : IViewModelState