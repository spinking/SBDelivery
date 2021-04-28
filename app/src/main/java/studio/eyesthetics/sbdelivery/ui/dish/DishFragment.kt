package studio.eyesthetics.sbdelivery.ui.dish

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_dish.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.extensions.formatToRub
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.viewmodels.DishViewModel
import studio.eyesthetics.sbdelivery.viewmodels.DishViewModelFactory
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

    override val prepareToolbar: (ToolbarBuilder.() -> Unit)? = {}

    override fun setupViews() {
        initViews()
    }

    private fun initViews() {
        val oldPriceText = SpannableString(780.formatToRub())
        oldPriceText.setSpan(StrikethroughSpan(), 0, oldPriceText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_old_price.text = oldPriceText
    }
}