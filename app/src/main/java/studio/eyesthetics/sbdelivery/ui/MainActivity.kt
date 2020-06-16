package studio.eyesthetics.sbdelivery.ui

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.base.BaseActivity
import studio.eyesthetics.sbdelivery.viewmodels.MainViewModel
import studio.eyesthetics.sbdelivery.viewmodels.MainViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.GenericSavedStateViewModelFactory
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
        GenericSavedStateViewModelFactory(mainViewModelFactory, this)
    }

    private lateinit var appBarConfiguration: AppBarConfiguration


/*    @Inject
    lateinit var pref: Pref

    private lateinit var fusedLocationClient: FusedLocationProviderClient*/

    private val PERMISSIONS_REQUEST = 1

    override fun subscribeOnState(state: IViewModelState) {
        //Do something with state
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Handler().postDelayed({}, 3000)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        //setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        this.supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }
        toolbar.navigationIcon = null

        navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )

        navController.setGraph(R.navigation.main_graph, intent?.extras)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        //nav_view.setupWithNavController(navController)

        //initBottombar(navController)

        btn_back.setOnClickListener {
            this.onBackPressed()
        }

/*        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener { instanceIdResult ->
                //val token = instanceIdResult.token
                //pref.authToken = token
            }*/

        //getDynamicLinks()

        //requestPermission()
        //updateLocale()
        //getLastKnownLocation()

        //guideline.setGuidelinePercent(1f)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /*    private fun getDynamicLinks() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener {
                var deepLink: Uri? = null
                if(it != null) {
                    deepLink = it.link
                }
            }
            .addOnFailureListener {
                Timber.e(it)
            }
    }*/

/*    private fun requestPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        val permissionHelper = PermissionHelper(this)
        permissionHelper.checkPermissions(permissions, PERMISSIONS_REQUEST)
    }*/

/*    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST -> requestPermission()
        }
    }*/

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
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun renderNotification(notify: Notify) {
        val snackbar = Snackbar.make(container, notify.message, Snackbar.LENGTH_LONG)

        /*if(bottombar != null) snackbar.anchorView = bottombar
        else snackbar.anchorView = nav_view*/

        /* when(notify) {
             is Notify.ActionMessage -> {
                 val (_, label, handler) = notify

                 with(snackbar) {
                     setActionTextColor(getColor(R.color.color_accent_dark))
                     setAction(label) { handler.invoke() }
                 }
             }

             is Notify.ErrorMessage -> {

                 val (_, label, handler) = notify

                 with(snackbar) {
                     setBackgroundTint(getColor(R.color.design_default_color_error))
                     setTextColor(getColor(android.R.color.white))
                     setActionTextColor(getColor(android.R.color.white))
                     handler ?: return@with
                     setAction(label) { handler.invoke() }
                 }
             }
         }

         snackbar.show()*/
    }
}

