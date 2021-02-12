package com.example.tiprankstask.data.entities

data class AuthorEntity (

	val name : String,
	val slug : String,
	val type : String,
	val bio : String,
	val image : ImageEntity,
	val created : String
)