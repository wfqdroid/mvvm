package www.imguiderx.mvvmapp.ui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import www.imguiderx.mvvmapp.network.ApiNetWork

class ArticleRepository(private val network: ApiNetWork) {
//    suspend fun getArticle() = withContext(Dispatchers.IO){
//        network.getArticle()?.datas
//    }
    suspend fun getArticle() = network.getArticle()?.datas
}