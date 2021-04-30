package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.models.reviews.AddReviewRequest
import studio.eyesthetics.sbdelivery.data.network.errors.ApiError
import studio.eyesthetics.sbdelivery.data.repositories.reviews.IReviewRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.*
import javax.inject.Inject

class ReviewDialogViewModel(
    handle: SavedStateHandle,
    private val reviewRepository: IReviewRepository
) : BaseViewModel<ReviewDialogState>(handle, ReviewDialogState()) {

    private val errHandler: ((Throwable) -> Unit) = {
        when(it) {
            is ApiError.Forbidden -> {
                notify(Notify.ErrorMessage(it.message))
                navigate(NavigationCommand.To(R.id.loginFragment))
            }
            is ApiError.Unauthorized -> {
                notify(Notify.ErrorMessage(it.message))
                navigate(NavigationCommand.To(R.id.loginFragment))
            }
            else -> notify(Notify.TextMessage("Ошибка при отправке. Попробуйте позже”"))
        }
    }

    fun handleAddReview(dishId: String, rating: Int, review: String) {
        launchSafety(
            errHandler = errHandler,
            isShowBlockingLoading = true
        ) {
            reviewRepository.addReview(dishId, AddReviewRequest(rating, review))
            updateState { it.copy(isReviewAdded = true) }
        }
    }

    fun handleAddButtonEnabling(isEnabled: Boolean) {
        updateState { it.copy(isAddButtonEnabled = isEnabled) }
    }
}

class ReviewDialogViewModelFactory @Inject constructor(
    private val reviewRepository: IReviewRepository
) : IViewModelFactory<ReviewDialogViewModel> {
    override fun create(handle: SavedStateHandle): ReviewDialogViewModel {
        return ReviewDialogViewModel(handle, reviewRepository)
    }
}

data class ReviewDialogState(
    val isAddButtonEnabled: Boolean = false,
    val isReviewAdded: Boolean = false
) : IViewModelState