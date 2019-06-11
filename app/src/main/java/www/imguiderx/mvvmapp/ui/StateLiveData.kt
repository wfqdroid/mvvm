package www.imguiderx.mvvmapp.ui

import androidx.lifecycle.MutableLiveData

class StateLiveData<T> : MutableLiveData<T>() {

    enum class State {
        Idle, Loading, Success, Error
    }

    val state = MutableLiveData<State>()

    init {
        initState()
    }

    fun postValueAndSuccess(value: T) {
        super.postValue(value)
        postSuccess()
    }

    private fun initState() {
        state.postValue(State.Idle)
    }

    fun postLoading() {
        state.postValue(State.Loading)
    }

    fun postSuccess() {
        state.postValue(State.Success)
    }

    fun postError() {
        state.postValue(State.Error)
    }
}