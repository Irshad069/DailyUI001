package com.dailyui001.base

import androidx.lifecycle.ViewModel
import com.dailyui001.extention.vmScopeMain
import com.dailyui001.ui.navigation.NavigationAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _baseUIEvents = MutableSharedFlow<BaseViewModelEvents>()
    val baseUIEvents = _baseUIEvents.asSharedFlow()


    protected fun showError(msg: String) {
        vmScopeMain {
            hideLoader()
            _baseUIEvents.emit(BaseViewModelEvents.ShowError(msg))
        }
    }

    protected fun showLoader() {
        vmScopeMain {
            _baseUIEvents.emit(BaseViewModelEvents.ShowLoader(true))
        }
    }

    protected fun hideLoader() {
        vmScopeMain {
            _baseUIEvents.emit(BaseViewModelEvents.ShowLoader(false))
        }
    }

    protected fun showToast(msg: String) {
        vmScopeMain {
            _baseUIEvents.emit(BaseViewModelEvents.ShowToast(msg))
        }
    }

    protected fun navigate(route: NavigationAction) {
        hideLoader()
        CoroutineScope(Dispatchers.Main).launch {
            _baseUIEvents.emit(BaseViewModelEvents.Navigate(route))
        }
    }

    protected fun navigateBack() {
        navigate(NavigationAction.PopBackStack)
    }

    sealed class BaseViewModelEvents {
        data class Navigate(val route: NavigationAction) : BaseViewModelEvents()
        data class ShowError(val msg: String) : BaseViewModelEvents()
        data class ShowToast(val msg: String) : BaseViewModelEvents()
        data class ShowLoader(val show: Boolean) : BaseViewModelEvents()
    }
}