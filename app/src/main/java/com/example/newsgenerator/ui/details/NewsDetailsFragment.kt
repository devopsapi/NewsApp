package com.example.newsgenerator.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newsgenerator.R
import com.example.newsgenerator.databinding.FragmentNewsDetailsBinding
import com.example.newsgenerator.ui.NewsListViewModel
import com.example.newsgenerator.utils.prettyDateTimeFormat

class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private val newsListViewModel: NewsListViewModel by activityViewModels()
    private val safeArgs: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        val article = newsListViewModel.getArticle(safeArgs.position)
        binding.apply {
            tvTitle.text = article.title
            tvSource.text = article.source.name
            tvDate.text = prettyDateTimeFormat(article.publishedAt)

            Glide.with(binding.root.context)
                .load(article.urlToImage)
                .error(R.drawable.ic_baseline_photo_24)
                .into(binding.ivNews)

            tvDescription.text = article.description

            webView.apply {
                settings.domStorageEnabled = true
                settings.javaScriptEnabled = true
                settings.loadsImagesAutomatically = true
                scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                webViewClient = WebViewClient()
                loadUrl(article.url)
            }
        }
    }
}