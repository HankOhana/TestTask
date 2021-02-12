package com.example.tiprankstask.data.entities

sealed class PostAdapterItemEntity {
    data class PostItem(val post: PostEntity) : PostAdapterItemEntity()
    data class PromotionItem(val link: String) : PostAdapterItemEntity()
}