package studio.eyesthetics.sbdelivery.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.extensions.hideSoftKeyboard
import studio.eyesthetics.sbdelivery.ui.base.BaseActivity
import studio.eyesthetics.sbdelivery.ui.menu.MenuFragment
import studio.eyesthetics.sbdelivery.viewmodels.MainViewModel
import studio.eyesthetics.sbdelivery.viewmodels.MainViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.Notify
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@MainActivity)
    }

    override val layout: Int = R.layout.activity_main

    @Inject
    internal lateinit var mainViewModelFactory: MainViewModelFactory

    public override val viewModel: MainViewModel by viewModels {
        SavedStateViewModelFactory(mainViewModelFactory, this)
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun subscribeOnState(state: IViewModelState) {
        //Do something with state
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        this.supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }
        toolbar.navigationIcon = null

        if (savedInstanceState != null) return

        navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )

        navController.setGraph(R.navigation.main_graph, intent?.extras)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        btn_back.setOnClickListener {
            this.onBackPressed()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.menu_container, MenuFragment(), "menuFragment")
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (
                v is SearchView.SearchAutoComplete ||
                v is EditText ||
                v is TextInputEditText
            ) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    v.hideSoftKeyboard()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun renderNotification(notify: Notify) {
        when (notify) {
            is Notify.ActionMessage -> {
                val builder = AlertDialog.Builder(this)
                builder.apply {
                    setTitle(notify.actionLabel)
                    setMessage(notify.message)
                    setPositiveButton(
                        getString(R.string.button_ok)
                    ) { dialog, id ->
                        notify.actionHandler.invoke()
                        dialog.dismiss()
                    }
                }
                builder.show()
            }
            is Notify.TextMessage -> {
                Toast.makeText(this, notify.message, Toast.LENGTH_SHORT).show()
            }
            is Notify.ErrorMessage -> {
                Toast.makeText(this, notify.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

