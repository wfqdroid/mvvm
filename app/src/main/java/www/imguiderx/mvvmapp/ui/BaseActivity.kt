package www.imguiderx.mvvmapp.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import www.imguiderx.mvvmapp.loge

open class BaseActivity : AppCompatActivity() {

    fun <T> post(liveData: StateLiveData<T>): StateLiveData<T> {
        liveData.state.observe(this, Observer {
            when (it) {
                StateLiveData.State.Loading -> showLoading()
                StateLiveData.State.Error -> showEror()
                StateLiveData.State.Success -> hideLoading()
            }
        })
        return liveData
    }

    fun showLoading() {
        loge("loading")
    }

    fun showEror() {
        loge("error")
    }

    fun hideLoading() {
    }
}