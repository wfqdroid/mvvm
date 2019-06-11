package www.imguiderx.mvvmapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel:ViewModel() {
    fun<T> launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit, liveData: StateLiveData<T>, isShowLoading:Boolean = true) = viewModelScope.launch {
        try {
            if(isShowLoading){
                liveData.postLoading()
            }
            block()
        } catch (e: Throwable) {
            liveData.postError()
            error(e)
        }finally {
            liveData.postSuccess()
        }
    }
}