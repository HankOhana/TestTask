package com.example.tiprankstask.data.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.example.tiprankstask.data.datasource.PostsDataSource
import com.example.tiprankstask.data.entities.PostAdapterItemEntity
import com.example.tiprankstask.data.entities.PostEntity
import com.example.tiprankstask.network.ApiService
import com.example.tiprankstask.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostsViewModel(private val apiService: ApiService = ApiService.getApiService()) : ViewModel() {

    var searchText = MutableLiveData<String?>()

    private val mPostsData = getPostsFlow()
        .map { pagingData -> pagingData.map {PostAdapterItemEntity.PostItem(it)} }
        .map {
            it.insertSeparators { before, after ->
                when {
                    before == null -> return@insertSeparators null
                    after == null -> return@insertSeparators null
                    shouldSeparate(before) -> PostAdapterItemEntity.PromotionItem("")
                    else -> null
                }
            }
        }
        .cachedIn(viewModelScope)
        .asLiveData()
            .let { it as MutableLiveData<PagingData<PostAdapterItemEntity>> }

    val postsData : LiveData<PagingData<PostAdapterItemEntity>> = mPostsData

    fun clearData() {
        val data = postsData.value ?: return
        data.filter { false }.let { mPostsData.value = it }
    }

    private fun getPostsFlow() : Flow<PagingData<PostEntity>> {
            return Pager(PagingConfig(pageSize = Constants.PAGE_SIZE), initialKey = 1) {
                PostsDataSource(apiService, searchText.value)
            }.flow
    }

    private fun shouldSeparate(before: PostAdapterItemEntity.PostItem) : Boolean {
        before.post.marker?.let {
            if (it%10 == 3) {
                return true
            }
        }
        return false
    }
}