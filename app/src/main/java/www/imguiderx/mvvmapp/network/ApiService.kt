package www.imguiderx.mvvmapp.network

import retrofit2.Call
import retrofit2.http.GET
import www.imguiderx.mvvmapp.model.Article
import www.imguiderx.mvvmapp.model.BaseRes

interface ApiService {
    @GET("/article/list/0/json")
    fun getArticle(): Call<BaseRes<Article>>
}