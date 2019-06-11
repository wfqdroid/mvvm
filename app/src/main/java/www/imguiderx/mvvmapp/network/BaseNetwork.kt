package www.imguiderx.mvvmapp.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import www.imguiderx.mvvmapp.loge
import www.imguiderx.mvvmapp.model.BaseRes
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

open class BaseNetwork {

    suspend fun <T> Call<T>.await(): T {
        loge("开始请求网络")
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                    loge("请求失败:${t.message}")
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    loge("请求成功::${Thread.currentThread().name}")
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            body as BaseRes<T>
                            if (body.errorCode == 0)
                                it.resume(body)
                            else if(body.errorCode == 100){
                                // 比方说登录超时等

                            }else{
                                it.resumeWithException(RuntimeException(body.errorMsg))
                            }
                        } else {
                            it.resumeWithException(RuntimeException("response body is null"))
                        }
                    } else {
                        loge(404)
                    }

                }
            })
        }
    }
}