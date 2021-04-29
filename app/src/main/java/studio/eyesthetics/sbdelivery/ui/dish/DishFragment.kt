package studio.eyesthetics.sbdelivery.ui.dish

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.request.CachePolicy
import kotlinx.android.synthetic.main.fragment_dish.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem
import studio.eyesthetics.sbdelivery.extensions.formatToRub
import studio.eyesthetics.sbdelivery.extensions.roundTo
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.ReviewDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.ReviewDiffCallback
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.Binding
import studio.eyesthetics.sbdelivery.ui.base.DelegationPageListAdapter
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.ui.delegates.RenderProp
import studio.eyesthetics.sbdelivery.viewmodels.DishState
import studio.eyesthetics.sbdelivery.viewmodels.DishViewModel
import studio.eyesthetics.sbdelivery.viewmodels.DishViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class DishFragment : BaseFragment<DishViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@DishFragment)
    }

    @Inject
    internal lateinit var dishViewModelFactory: DishViewModelFactory

    override val layout: Int = R.layout.fragment_dish
    override val viewModel: DishViewModel by viewModels {
        SavedStateViewModelFactory(dishViewModelFactory, this)
    }
    override val binding: DishBinding by lazy { DishBinding() }
    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
        this.setTitle(args.dish.name)
    }
    private val args: DishFragmentArgs by navArgs()
    private val reviewDiffCallback = ReviewDiffCallback()
    private val reviewAdapter by lazy { DelegationPageListAdapter(reviewDiffCallback) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(ReviewDialogFragment.Add_KEY) { _, bundle ->
            viewModel.handleAddReview(
                bundle[ReviewDialogFragment.RATING_KEY] as Int,
                bundle[ReviewDialogFragment.REVIEW_KEY] as String
            )
        }
    }

    override fun setupViews() {
        viewModel.handleDishId(args.dish.id)
        initViews()
        initAdapter()

        viewModel.observeReviews(viewLifecycleOwner) {
            reviewAdapter.submitList(it)
        }
    }

    private fun initAdapter() {
        reviewAdapter.delegatesManager.addDelegate(ReviewDelegate())

        rv_reviews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reviewAdapter
        }
    }

    private fun initViews() {
        val dishItem: DishItem = args.dish
        val oldPriceText = SpannableString(dishItem.oldPrice.formatToRub())
        oldPriceText.setSpan(StrikethroughSpan(), 0, oldPriceText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_old_price.text = oldPriceText

        iv_picture.load(dishItem.image) {
            diskCachePolicy(CachePolicy.ENABLED)
        }
        tv_stock.isVisible = dishItem.oldPrice.isNotEmpty()
        cb_favorite.apply {
            isChecked = dishItem.isFavorite
            setOnClickListener {
                viewModel.handleFavorite(dishItem.id, cb_favorite.isChecked)
            }
        }
        tv_title.text = dishItem.name
        tv_description.text = dishItem.description
        tv_new_price.text = dishItem.price.formatToRub()
        btn_decrement.setOnClickListener {
            viewModel.handleCount(-1)
        }
        btn_increment.setOnClickListener {
            viewModel.handleCount(1)
        }
        btn_add.setOnClickListener {
            //tODO add to basket
        }
        val ratingText = "${dishItem.rating.roundTo()}/5"
        tv_reviews_rating.text = ratingText

        btn_add_review.setOnClickListener {
            val action = DishFragmentDirections.actionDishFragmentToReviewDialogFragment()
            viewModel.navigate(NavigationCommand.To(action.actionId))
        }
    }

    inner class DishBinding : Binding() {
        private var itemsCount: Int by RenderProp(1) {
            tv_count.text = "$itemsCount"
        }
        private var isDecrementButtonActive: Boolean by RenderProp(false) {
            btn_decrement.isEnabled = it
        }
        override fun bind(data: IViewModelState) {
            data as DishState
            itemsCount = data.itemsCount
            isDecrementButtonActive = data.isDecrementButtonActive
        }
    }
}