package www.imguiderx.mvvmapp.model

class BaseRes<T> {
    var errorCode: Int = 0
    var errorMsg: String = ""
    var data: T? = null
}