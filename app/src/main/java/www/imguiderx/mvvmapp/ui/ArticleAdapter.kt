package www.imguiderx.mvvmapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import www.imguiderx.mvvmapp.R
import www.imguiderx.mvvmapp.databinding.ItemArticleBinding
import www.imguiderx.mvvmapp.model.Data

class ArticleAdapter (private val mList: List<Data>) : RecyclerView.Adapter<ArticleAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        val bind = DataBindingUtil.bind<ItemArticleBinding>(v)
        return bind?.let { MyViewHolder(it) }!!
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val binding = holder.binding
        binding.data = mList[position]
    }


    inner class MyViewHolder(
        var binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root)
}