package studio.eyesthetics.sbdelivery.ui.base

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import studio.eyesthetics.sbdelivery.ui.MainActivity
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.Loading
import studio.eyesthetics.sbdelivery.viewmodels.base.Notify

abstract class BaseDialogFragment<T : BaseViewModel<out IViewModelState>> : DialogFragment() {
    val main: MainActivity
        get() = activity as MainActivity
    open val binding: Binding? = null
    protected abstract val viewModel: T
    protected abstract val layout: Int

    abstract fun setupViews()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //restore state
        viewModel.restoreState()

        //owner it is view
        viewModel.observeState(viewLifecycleOwner) { binding?.bind(it) }
        //bind default values if viewmodel not loaded data
        if(binding?.isInflated == false) binding?.onFinishInflate()

        viewModel.observeNotifications(viewLifecycleOwner) { main.renderNotification(it) }
        viewModel.observeNavigation(viewLifecycleOwner) { main.viewModel.navigate(it) }
        viewModel.observeLoading(viewLifecycleOwner) { renderLoading(it) }
        binding?.restoreUi(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        setupViews()
        binding?.rebind()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState()
        binding?.saveUi(outState)
        super.onSaveInstanceState(outState)
    }

    open fun renderNotification(notify: Notify) {
        main.renderNotification(notify)
    }

    private fun renderLoading(loadingState: Loading) {
        main.renderLoading(loadingState)
    }
}