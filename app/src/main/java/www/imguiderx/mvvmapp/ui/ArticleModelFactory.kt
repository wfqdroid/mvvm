package www.imguiderx.mvvmapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ArticleModelFactory(private val repository: ArticleRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(repository) as T
    }
}