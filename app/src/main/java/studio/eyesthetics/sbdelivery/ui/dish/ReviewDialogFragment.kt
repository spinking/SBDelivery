package studio.eyesthetics.sbdelivery.ui.dish

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_review_dialog.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.extensions.dpToPx
import studio.eyesthetics.sbdelivery.ui.base.BaseDialogFragment
import studio.eyesthetics.sbdelivery.ui.base.Binding
import studio.eyesthetics.sbdelivery.ui.delegates.RenderProp
import studio.eyesthetics.sbdelivery.viewmodels.ReviewDialogState
import studio.eyesthetics.sbdelivery.viewmodels.ReviewDialogViewModel
import studio.eyesthetics.sbdelivery.viewmodels.ReviewDialogViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class ReviewDialogFragment : BaseDialogFragment<ReviewDialogViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@ReviewDialogFragment)
    }

    @Inject
    internal lateinit var reviewDialogViewModelFactory: ReviewDialogViewModelFactory

    override val layout: Int = R.layout.fragment_review_dialog
    override val viewModel: ReviewDialogViewModel by viewModels {
        SavedStateViewModelFactory(reviewDialogViewModelFactory, this)
    }
    override val binding: ReviewDialogBinding by lazy { ReviewDialogBinding() }

    override fun setupViews() {

        btn_cancel.setOnClickListener { dismiss() }

        rb_rate_user.setOnRatingChangeListener { _, rating ->
            viewModel.handleAddButtonEnabling(rating > 0)
        }

        btn_add.setOnClickListener {
            val rating = rb_rate_user.rating.toInt()
            if (rating > 0) {
                val dishId = requireArguments().getString("dishId") ?: ""
                viewModel.handleAddReview(dishId, rating, et_review.text.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val displayWidth = resources.displayMetrics.widthPixels
        val params = dialog?.window?.attributes
        params?.width = displayWidth - (DEFAULT_MARGIN * 2).dpToPx()
        dialog?.window?.attributes = params
    }

    private fun resumeToDishFragment() {
        setFragmentResult(Add_KEY, bundleOf())
        dismiss()
    }

    companion object {
        private const val DEFAULT_MARGIN = 30
        const val Add_KEY = "ADD_KEY"
    }

    inner class ReviewDialogBinding : Binding() {
        private var isAddButtonEnabled: Boolean by RenderProp(false) {
            btn_add.isEnabled = it
            btn_add.isActivated = it
        }

        private var isReviewAdded: Boolean by RenderProp(false) {
            if (it) resumeToDishFragment()
        }
        override fun bind(data: IViewModelState) {
            data as ReviewDialogState
            isAddButtonEnabled = data.isAddButtonEnabled
            isReviewAdded = data.isReviewAdded
        }
    }
}