package studio.eyesthetics.sbdelivery.ui.base

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlin.properties.Delegates

class ProgressDialogManager(
    private val fragmentManager: FragmentManager
) {

    var showedProgressCount: Int by Delegates.observable(0) { _, oldValue, newValue ->
        when {
            newValue == 1 && oldValue == 0 -> showFullscreenProgress()
            newValue == 0 -> hideFullscreenProgress()
        }
    }

    fun showProgressBar() {
        if (showedProgressCount > 0)
            showFullscreenProgress()
    }

    private fun showFullscreenProgress() {
        FullScreenProgressDialog().show(fragmentManager, FULL_SCREEN_PROGRESS_TAG)
    }

    private fun hideFullscreenProgress() {
        fragmentManager.fragments
            .forEach { fragment ->
                if ((fragment as? DialogFragment)?.tag == FULL_SCREEN_PROGRESS_TAG) {
                    fragment.dismiss()
                }
            }
    }

    companion object {
        private const val FULL_SCREEN_PROGRESS_TAG = "FULL_SCREEN_PROGRESS"
    }
}