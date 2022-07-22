package com.example.newsgenerator.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsgenerator.data.models.Article
import com.example.newsgenerator.databinding.FragmentNewsListBinding
import com.example.newsgenerator.ui.NewsListViewModel
import com.example.newsgenerator.ui.adapters.NewsAdapter
import com.example.newsgenerator.ui.adapters.OnNewsClickListener
import com.example.newsgenerator.utils.getCountry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private lateinit var binding: FragmentNewsListBinding
    private val newsListViewModel: NewsListViewModel by activityViewModels()
    private val newsAdapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSearch()
        setUpSwipeRefreshLayout()
        setUpAdapter()
        setUpRecyclerView()
        observeData()
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRL.setOnRefreshListener {
            newsListViewModel.getHeadlines(getCountry())
        }
    }

    private fun setUpAdapter() {
        newsAdapter.setNewsOnClickListener(
            object : OnNewsClickListener {
                override fun onClick(id: Int) {
                    findNavController().navigate(
                        NewsListFragmentDirections.actionNewsListFragmentToNewsDetailsFragment(
                            id
                        )
                    )
                }
            }
        )
    }

    private fun setUpSearch() {
        binding.searchBtn.setOnClickListener {
            if (binding.etQuery.text.isNullOrEmpty()) {
                newsListViewModel.getHeadlines(getCountry())
            } else {
                newsListViewModel.getSpecificHeadlines(binding.etQuery.text.toString())
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun observeData() {
        newsListViewModel.apply {
            articles.observe(viewLifecycleOwner) { articles ->
                newsAdapter.setData(articles as ArrayList<Article>)
            }
            progressBar.observe(viewLifecycleOwner) { isRefreshing ->
                binding.swipeRL.isRefreshing = isRefreshing
            }
            errorMessage.observe(viewLifecycleOwner) { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}