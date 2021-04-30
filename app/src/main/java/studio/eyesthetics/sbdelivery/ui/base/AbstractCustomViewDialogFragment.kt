package studio.eyesthetics.sbdelivery.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import studio.eyesthetics.sbdelivery.R

abstract class AbstractCustomViewDialogFragment : AppCompatDialogFragment() {

    protected abstract val layoutResId: Int

    abstract fun initializeView(view: View)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AlertDialogWithTransparentBackground)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val layoutInflater = activity?.layoutInflater ?: inflater
        return layoutInflater.inflate(layoutResId, container, false).also { initializeView(it) }
    }

}
