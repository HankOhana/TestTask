package com.example.tiprankstask.network

import com.example.tiprankstask.network.response.PostsResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/news/posts")
    suspend fun fetchPosts(
        @Query("page") number: Int,
        @Query("per_page") per_page: Int,
        @Query("search") text: String?
    ): Response<PostsResponse>

    companion object {

        private const val BASE_URL = "https://www.tipranks.com/"

        fun getApiService(): ApiService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(ApiService::class.java)
    }
}