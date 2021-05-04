package studio.eyesthetics.sbdelivery.ui.registrationorder

import android.os.Bundle
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_registration_order.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.models.orders.CreateOrderRequest
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.Binding
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.ui.delegates.RenderProp
import studio.eyesthetics.sbdelivery.ui.registrationorder.SetAddressFragment.Companion.ADDRESS
import studio.eyesthetics.sbdelivery.viewmodels.RegistrationOrderState
import studio.eyesthetics.sbdelivery.viewmodels.RegistrationOrderViewModel
import studio.eyesthetics.sbdelivery.viewmodels.RegistrationOrderViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class RegistrationOrderFragment : BaseFragment<RegistrationOrderViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@RegistrationOrderFragment)
    }

    @Inject
    internal lateinit var registrationOrderViewModelFactory: RegistrationOrderViewModelFactory

    override val layout: Int = R.layout.fragment_registration_order
    override val viewModel: RegistrationOrderViewModel by viewModels {
        SavedStateViewModelFactory(registrationOrderViewModelFactory, this)
    }
    override val binding: RegistrationOrderBinding by lazy { RegistrationOrderBinding() }
    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
        this.setTitle(getString(R.string.registration_order_label))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(SetAddressFragment.ADDRESS_KEY) {_, bundle ->
            viewModel.handleChangeAddress(bundle[ADDRESS] as String)
        }
    }

    override fun setupViews() {

        btn_enter.setOnClickListener {
            viewModel.navigate(NavigationCommand.To(R.id.setAddressFragment))
        }
        btn_map.setOnClickListener {  }
        btn_register_order.setOnClickListener {
            viewModel.handleCreateOrder(CreateOrderRequest(
                tv_address.text.toString(),
                et_entrance.text.toString().toInt(),
                et_floor.text.toString().toInt(),
                et_room.text.toString(),
                et_intercom.text.toString(),
                et_comment.text.toString()
            ))
        }
    }

    inner class RegistrationOrderBinding : Binding() {
        private var isOrderButtonEnabled: Boolean by RenderProp(false) {
            btn_register_order.isEnabled = it
        }
        private var address: String by RenderProp("") {
            tv_address.text = it
        }
        override fun bind(data: IViewModelState) {
            data as RegistrationOrderState
            isOrderButtonEnabled = data.isOrderButtonEnabled
            address = data.address
        }
    }
}