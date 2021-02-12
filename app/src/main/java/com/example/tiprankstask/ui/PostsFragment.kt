package com.example.tiprankstask.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiprankstask.R
import com.example.tiprankstask.adapters.PostsAdapter
import com.example.tiprankstask.adapters.PostsLoadStateAdapter
import com.example.tiprankstask.data.entities.PostAdapterItemEntity
import com.example.tiprankstask.data.viewmodel.PostsViewModel
import com.example.tiprankstask.databinding.FragmentPostsBinding
import com.example.tiprankstask.utils.Constants


class PostsFragment : Fragment(R.layout.fragment_posts) {

    lateinit var postViewModel: PostsViewModel
    lateinit var viewBinding: FragmentPostsBinding

    private val postsAdapter by lazy { PostsAdapter { item -> onItemClicked(item) } }
    private val loadAdapter by lazy { PostsLoadStateAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postViewModel = defaultViewModelProviderFactory.create(PostsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentPostsBinding.bind(view)
        setupView()
    }

    private fun setupView() {
        postViewModel.postsData.observe(viewLifecycleOwner, { pagingData ->
            postsAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        })
        postsAdapter.apply {
            addLoadStateListener {
                viewBinding.apply {
                    mainProgress.isVisible = it.refresh is LoadState.Loading
                    retryButton.isVisible = it.refresh is LoadState.Error
                }
            }
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    if (positionStart == 0) {
                        viewBinding.postsRecyclerView.scrollToPosition(0)
                    }
                }
            })
        }
        viewBinding.apply{
            postsRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = postsAdapter.withLoadStateFooter(loadAdapter)
            }
            retryButton.setOnClickListener { postsAdapter.retry() }
        }
    }

    private fun refreshData(searchText: String? = null) {
        loadAdapter.notifyDataSetChanged()
        postViewModel.searchText.value = searchText
        postViewModel.clearData()
        postsAdapter.refresh()
    }

    private fun onItemClicked(item: PostAdapterItemEntity) {
        when(item) {
            is PostAdapterItemEntity.PostItem -> {
                val action = PostsFragmentDirections.actionPostsToPostItem(item.post.link)
                this.findNavController().navigate(action)
            }
            is PostAdapterItemEntity.PromotionItem -> {
                // Maybe try catch needed
                Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PROMOTION_LINK)).also {
                    startActivity(it)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.search)
        searchItem?.let {
            val searchView = it.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { text ->
                        refreshData(text)
                    }
                    searchView.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            it.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                    postViewModel.searchText.value?.let { refreshData() }
                    return true
                }
            })
            postViewModel.searchText.value?.let { text ->
                searchItem.expandActionView()
                searchView.clearFocus()
                searchView.setQuery(text, false)
            }
        }
    }
}