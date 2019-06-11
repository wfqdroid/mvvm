package www.imguiderx.mvvmapp.model

class BaseRes<T> {
    var code: Int = 0
    var message: String = ""
    var data: T? = null

    fun isSuccess(): Boolean {
        return code == 0
    }
}