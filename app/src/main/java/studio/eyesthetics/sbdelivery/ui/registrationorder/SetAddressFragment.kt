package studio.eyesthetics.sbdelivery.ui.registrationorder

import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_set_address.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.AddressDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.AddressDiffCallback
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.Binding
import studio.eyesthetics.sbdelivery.ui.base.DelegationAdapter
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.ui.delegates.RenderProp
import studio.eyesthetics.sbdelivery.viewmodels.SetAddressState
import studio.eyesthetics.sbdelivery.viewmodels.SetAddressViewModel
import studio.eyesthetics.sbdelivery.viewmodels.SetAddressViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class SetAddressFragment : BaseFragment<SetAddressViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@SetAddressFragment)
    }

    @Inject
    internal lateinit var setaddressViewModelFactory: SetAddressViewModelFactory

    override val layout: Int = R.layout.fragment_set_address
    override val viewModel: SetAddressViewModel by viewModels {
        SavedStateViewModelFactory(setaddressViewModelFactory, this)
    }
    override val binding: SetAddressBinding by lazy { SetAddressBinding() }
    private val diffCallback = AddressDiffCallback()
    private val addressAdapter by lazy { DelegationAdapter(diffCallback) }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
        this.setTitle(getString(R.string.set_address_label))
    }

    override fun setupViews() {
        initAdapter()
        et_address.addTextChangedListener {
            viewModel.handleChangeAddress(it.toString())
        }
        btn_choice_address.setOnClickListener {
            setFragmentResult(ADDRESS_KEY, bundleOf(ADDRESS to binding.address))
        }
    }

    private fun initAdapter() {
        addressAdapter.delegatesManager.addDelegate(AddressDelegate {
            viewModel.handleChangeAddress(it)
        })

        rv_address.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = addressAdapter
        }
    }

    companion object {
        const val ADDRESS_KEY = "ADDRESS_KEY"
        const val ADDRESS = "ADDRESS"
    }

    inner class SetAddressBinding : Binding() {
        var address: String by RenderProp("") {}
        override fun bind(data: IViewModelState) {
            data as SetAddressState
            address = data.address
        }
    }
}