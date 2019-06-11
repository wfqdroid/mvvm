package www.imguiderx.mvvmapp.ui
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import www.imguiderx.mvvmapp.R
import www.imguiderx.mvvmapp.databinding.ActivityMainBinding
import www.imguiderx.mvvmapp.network.ApiNetWork
import www.imguiderx.mvvmapp.toast

class MainActivity : BaseActivity() {

    private val viewModel by lazy {
        val modelFactory =
            ArticleModelFactory(ArticleRepository(ApiNetWork))
        ViewModelProviders.of(this,modelFactory).get(ArticleViewModel::class.java)
    }
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding.lifecycleOwner = this


        post(viewModel.data).observe(this, Observer {
            var adapter = ArticleAdapter(it)
            binding.rvList?.let { it1 ->
                it1.layoutManager = LinearLayoutManager(this)
                it1.addItemDecoration(DividerItemDecoration(3,"#e5e5e5"))
                it1.addOnItemTouchListener(RecyclerItemClickListener(this,it1, object : OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        toast(it[position].title)
                    }
                }))
            }
            binding.adapter = adapter
        })

        viewModel.getArticle()



    }
}

