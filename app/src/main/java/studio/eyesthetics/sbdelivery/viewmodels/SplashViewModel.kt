package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import studio.eyesthetics.sbdelivery.workers.SyncWorker
import javax.inject.Inject

class SplashViewModel(
    handle: SavedStateHandle
) : BaseViewModel<SplashState>(handle, SplashState()) {

    private val currentWork = WorkManager.getInstance().getWorkInfosByTagLiveData(SYNC_WORK_TAG)

    init {
        val syncWorker = OneTimeWorkRequestBuilder<SyncWorker>().addTag(SYNC_WORK_TAG).build()
        WorkManager.getInstance().enqueueUniqueWork(SYNC_WORK_TAG, ExistingWorkPolicy.KEEP, syncWorker)
    }

    fun observeCurrentWork(owner: LifecycleOwner, onChange: (List<WorkInfo>) -> Unit) {
        currentWork.observe(owner, Observer { onChange(it) })
    }

    fun handleHomeNavigation() {
        navigate(NavigationCommand.To(R.id.homeFragment))
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