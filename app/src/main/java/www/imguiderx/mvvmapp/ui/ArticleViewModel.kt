package www.imguiderx.mvvmapp.ui

import www.imguiderx.mvvmapp.model.Data

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