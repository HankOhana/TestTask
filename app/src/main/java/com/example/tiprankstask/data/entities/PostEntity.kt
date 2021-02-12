package com.example.tiprankstask.data.entities

data class PostEntity(
    var marker: Int?,
    val id : Int,
    var title : String,
    var slug : String,
    var date : String,
    var description : String,
    var image : ImageEntity,
    var thumbnail : ThumbnailEntity,
    var link : String,
    var author : AuthorEntity,
    var sticky : Boolean,
    var category : CategoryEntity,
    var topics : List<TopicsEntity>,
    var stocks : List<StocksEntity>
)
