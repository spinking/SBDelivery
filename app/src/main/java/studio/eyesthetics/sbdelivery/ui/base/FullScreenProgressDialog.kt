package studio.eyesthetics.sbdelivery.ui.base

import android.os.Bundle
import android.view.View
import studio.eyesthetics.sbdelivery.R

class FullScreenProgressDialog : AbstractCustomViewDialogFragment() {

    override val layoutResId: Int = R.layout.layout_progress

    override fun initializeView(view: View) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

}
