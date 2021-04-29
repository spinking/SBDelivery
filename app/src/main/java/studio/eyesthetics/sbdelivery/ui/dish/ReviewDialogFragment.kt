package studio.eyesthetics.sbdelivery.ui.dish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import kotlinx.android.synthetic.main.fragment_review_dialog.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.extensions.dpToPx

class ReviewDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_review_dialog, container, false)
    }

    override fun onResume() {
        super.onResume()
        val displayWidth = resources.displayMetrics.widthPixels
        val params = dialog?.window?.attributes
        params?.width = displayWidth - (DEFAULT_MARGIN * 2).dpToPx()
        dialog?.window?.attributes = params
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_cancel.setOnClickListener { dismiss() }

        btn_add.setOnClickListener {
            setFragmentResult(Add_KEY, bundleOf(RATING_KEY to rb_rate_user.rating.toInt(), REVIEW_KEY to et_review.text.toString()))
            dismiss()
        }
    }

    companion object {
        private const val DEFAULT_MARGIN = 30
        const val Add_KEY = "ADD_KEY"
        const val RATING_KEY = "RATING_KEY"
        const val REVIEW_KEY = "REVIEW_KEY"
    }
}