package com.example.newsgenerator.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsgenerator.R
import com.example.newsgenerator.data.models.Article
import com.example.newsgenerator.databinding.NewsItemBinding
import com.example.newsgenerator.utils.prettyDateTimeFormat

class NewsAdapter : RecyclerView.Adapter<BaseViewHolder<Article>>() {
    private var articles = ArrayList<Article>()
    private lateinit var newsClickListener: OnNewsClickListener

    @SuppressLint("NotifyDataSetChanged")
    fun setData(articles: ArrayList<Article>) {
        this.articles = articles
        this.notifyDataSetChanged()
    }

    fun setNewsOnClickListener(newsClickListener: OnNewsClickListener) {
        this.newsClickListener = newsClickListener
    }

    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        BaseViewHolder<Article>(binding.root) {

        override fun onBind(item: Article) {
            binding.apply {
                tvTitle.text = item.title
                tvSource.text = item.source.name
                tvDate.text = prettyDateTimeFormat(item.publishedAt)

                Glide.with(binding.root.context)
                    .load(item.urlToImage)
                    .error(R.drawable.ic_baseline_photo_24)
                    .into(binding.ivNews)

                root.setOnClickListener {
                    newsClickListener.onClick(articles.indexOf(item))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(NewsItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Article>, position: Int) {
        holder.onBind(articles[position])
    }

    override fun getItemCount(): Int = this.articles.size

}

interface OnNewsClickListener {
    fun onClick(id: Int)
}