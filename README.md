# MVVM
### 所使用到的技术：LiveData,ViewModel,kotlin,kotlin协程，DataBinding等
> 先简单介绍一下上面几个
* LiveData:见名知意，观察订阅模式，用于响应式编程，我们都知道RxJava的强大，但是LiveData相比于其，根本
不用管生命周期，应用会在页面"活着"的时候将数据同时到页面，当页面不可见活着销毁的时候，数据也就不会响应，具体的使用参照项目代码
* ViewModel
官方给出的介绍是，持有页面需要的数据，当手机旋转的时候，不会销毁数据。同时也是MVVM架构的VM层
* kotlin/kotlin协程
kotlin毋庸置疑，做Android的几乎都在学习，kotlin1.3之后，协程已经成了稳定版本，我们可以放心使用。使用协程之后，我们几乎可以不用管线程，
它比线程要轻量，与LiveData配合使用，当网络响应了我们数据的时候，不需要使用Handler做线程切换，也不需要使用RxJava的操作符做切换了。我们之前的开发，习惯了
接口回调数据了，而kotlin协程，则可以使用同步的方式做异步的操作，代码简洁高效
* DataBinding
谈到MVVM,很多人就离不开databinding,mvvm是一种思想，databing只是google给我们的一个工具，用来实现响应式编程，双向绑定的。

> 下面介绍一下项目的架构，就以我们常用的Activity为入口开始介绍

UI -> ViewModel -> Repository -> NetWork/Dao
箭头是单项的，也就是说，ViewModel持有Repository的引用，反过来没有，否则容易内存泄漏。
网络请求使用的是Retrofit,数据库Dao层这里省略了，最近太忙，等有时间再补充上去。Repository(仓库),负责提供数据，该数据可以从网络去取，也可以从数据库去取，
ViewModel持有Repository的引用，也就是可以将仓库的数据拿来自己持有，然后将数据给到UI层。大致的流程就是这样。
下面我们说一下项目中的细节：
> 先思考一个问题？在项目中，我们使用协程，当页面销毁的时候，我们怎么取消请求？
这里我么使用ViewModel自带的viewModelScope.launch,他会在页面销毁的时候自动取消请求，我们可以写一个BaseViewModel
```
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

class ArticleViewModel(private val respository: ArticleRepository) : BaseViewModel() {
    val data = StateLiveData<List<Data>>()

    fun getArticle() {
        launch({
            respository.getArticle()?.let {
                data.postValueAndSuccess(it)
            }
        }, {

        }, data)
    }
}

```
StateLiveData是一个由状态的LiveData,这样我们可以在BaseViewModel的launch里面直接发送loading等用于界面交互
```
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
```

> 我们如何根据服务端的状态码来进行不同操作？
这里我们采用在Retrofit的Call对象上面扩展了一个await的函数的方式,以后自己的模块的NetWork直接继承BaseNetWork即可
```
open class BaseNetwork {

    suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
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
                    }

                }
            })
        }
    }
}
```

参考：https://juejin.im/post/5ceddb14f265da1bcb4f0db6
     https://blog.csdn.net/guolin_blog/article/details/87900605
     https://developer.android.com/topic/libraries/architecture/livedata



