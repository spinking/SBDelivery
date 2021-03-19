package studio.eyesthetics.sbdelivery.viewmodels.base

import android.os.Bundle
import androidx.annotation.UiThread
import androidx.lifecycle.*
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.network.errors.ApiError
import studio.eyesthetics.sbdelivery.data.network.errors.NoNetworkError
import java.net.SocketTimeoutException

abstract class BaseViewModel<T : IViewModelState>(
    private val handleState: SavedStateHandle,
    initState: T
) : ViewModel() {
    private val loading = MutableLiveData(Loading.HIDE_LOADING)
    val notifications = MutableLiveData<Event<Notify>>()
    val navigation = MutableLiveData<Event<NavigationCommand>>()
    val permissions = MutableLiveData<Event<List<String>>>()

    val state: MediatorLiveData<T> = MediatorLiveData<T>().apply {
        value = initState
    }

    val currentState
        get() = state.value!!

    @UiThread
    protected inline fun updateState(update: (currentState: T) -> T) {
        val updatedState: T = update(currentState)
        state.value = updatedState
    }

    @UiThread
    protected fun notify(content: Notify) {
        notifications.value =
            Event(content)
    }

    open fun navigate(navigationCommand: NavigationCommand) {
        navigation.value = Event(navigationCommand)
    }

    fun requestPermissions(requestPermission: List<String>) {
        permissions.value = Event(requestPermission)
    }

    protected fun showLoading(loadingType: Loading = Loading.SHOW_LOADING) {
        loading.value = loadingType
    }

    protected fun hideLoading() {
        loading.value = Loading.HIDE_LOADING
    }

    protected fun launchSafety(
        errHandler: ((Throwable) -> Unit)? = null,
        compHandler: ((Throwable?) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        val errHand = CoroutineExceptionHandler { _, err ->
            errHandler?.invoke(err) ?: when (err) {
                is NoNetworkError -> notify(Notify.TextMessage("Network not available, check internet connection"))

                is SocketTimeoutException -> notify(
                    Notify.ActionMessage(
                        "Network timeout exception - please try again",
                        "Retry"
                    ) { launchSafety(errHandler, compHandler, block) })

                is ApiError.InternalServerError -> notify(
                    Notify.ErrorMessage(
                        err.message,
                        "Retry"
                    ) { launchSafety(errHandler, compHandler, block) })

                is ApiError.Forbidden -> {
                    notify(Notify.ErrorMessage(err.message))
                    navigate(NavigationCommand.To(R.id.loginFragment))
                }
                is ApiError.Unauthorized -> {
                    notify(Notify.ErrorMessage(err.message))
                    navigate(NavigationCommand.To(R.id.loginFragment))
                }

                is ApiError -> notify(Notify.ErrorMessage(err.message))
                else -> notify(Notify.ErrorMessage(err.message ?: "Something wrong"))
            }
        }

        (viewModelScope + errHand).launch {
            showLoading()
            block()
        }.invokeOnCompletion {
            hideLoading()
            compHandler?.invoke(it)
        }
    }

    fun observeState(owner: LifecycleOwner, onChanged: (newState: T) -> Unit) {
        state.observe(owner, Observer { onChanged(it!!) })
    }

    fun observeNotifications(owner: LifecycleOwner, onNotify: (notification: Notify) -> Unit) {
        notifications.observe(owner,
            EventObserver { onNotify(it) })
    }

    fun observeNavigation(owner: LifecycleOwner, onNavigate: (command: NavigationCommand) -> Unit) {
        navigation.observe(owner,
            EventObserver { onNavigate(it) })
    }

    fun observePermissions(owner: LifecycleOwner, handle: (permissions: List<String>) -> Unit) {
        permissions.observe(owner, EventObserver { handle(it) })
    }

    fun observeLoading(owner: LifecycleOwner, onChange: (newState: Loading) -> Unit) {
        loading.observe(owner, Observer { onChange(it) })
    }

    protected fun <S> subscribeOnDataSource(
        source: LiveData<S>,
        onChanged: (newValue: S, currentState: T) -> T?
    ) {
        state.addSource(source) {
            state.value = onChanged(it, currentState) ?: return@addSource
        }
    }

    fun saveState() {
        currentState.save(handleState)
    }

    @Suppress("UNCHECKED_CAST")
    fun restoreState() {
        state.value = currentState.restore(handleState) as T
    }

}

class Event<out E>(private val content: E) {
    var hasBeenHandled = false

    fun getContentIfNotHandled(): E? {
        return if (hasBeenHandled) null
        else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): E = content
}

class EventObserver<E>(private val onEventUnhandledContent: (E) -> Unit) : Observer<Event<E>> {

    override fun onChanged(event: Event<E>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}

sealed class Notify() {
    abstract val message: String
    data class TextMessage(override val message: String) : Notify()

    data class ActionMessage(
        override val message: String,
        val actionLabel: String,
        val actionHandler: (() -> Unit)
    ) : Notify()

    data class ErrorMessage(
        override val message: String,
        val errLabel: String? = null,
        val errHandler: (() -> Unit)? = null
    ) : Notify()
}

sealed class NavigationCommand() {
    data class To(
        val destination: Int,
        val args: Bundle? = null,
        val options: NavOptions? = null,
        val extras: Navigator.Extras? = null
    ) : NavigationCommand()

    data class StartLogin(
        val privateDestination: Int? = null
    ) : NavigationCommand()

    data class FinishLogin(
        val privateDestination: Int? = null
    ) : NavigationCommand()
    data class ReplaceAuth(
        val privateDestination: Int? = null
    ) : NavigationCommand()
    data class PopUpToDestination(
        val currentDestination: Int? = null
    ) : NavigationCommand()
}
enum class Loading {
    SHOW_LOADING, SHOW_BLOCKING_LOADING, HIDE_LOADING
}