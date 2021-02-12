package com.example.tiprankstask.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tiprankstask.data.entities.PostEntity
import com.example.tiprankstask.network.ApiService
import com.example.tiprankstask.utils.Constants

class PostsDataSource(private val apiService: ApiService, private val searchText: String? = null)
    : PagingSource<Int, PostEntity>() {

    private var index = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostEntity> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.fetchPosts(currentLoadingPageKey, Constants.PAGE_SIZE, searchText)
            val responseData = mutableListOf<PostEntity>()
            val data = response.body()?.data ?: emptyList()
            data.forEach {
                it.marker = ++index
            }
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = if (currentLoadingPageKey == -1) prevKey else null,
                nextKey = if (responseData.size < Constants.PAGE_SIZE) null
                else currentLoadingPageKey.plus(1)

            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostEntity>): Int? {
        return null
    }

}