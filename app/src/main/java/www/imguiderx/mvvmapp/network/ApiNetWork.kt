package www.imguiderx.mvvmapp.network

object ApiNetWork:BaseNetwork() {
    private val apiService = ServiceCreator.create(ApiService::class.java)
    suspend fun getArticle() = apiService.getArticle().await().data

}