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
                            if (body.code == 0)
                                it.resume(body)
                            else if(body.code == 100){
                                // 重新登录 记住：和回到首页是不一样的
                                // 重新登录，删除所有Activity栈里面的Activity,然后跳转到登录页面

                            }else{

                                it.resumeWithException(RuntimeException(body.message))
                            }
                        } else {
                            loge("响应体是空的")
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