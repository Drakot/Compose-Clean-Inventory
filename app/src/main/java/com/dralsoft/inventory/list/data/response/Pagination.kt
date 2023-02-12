package com.dralsoft.inventory.list.data.response

data class Pagination(
    val page: Int,
    val pageCount: Int,
    val pageSize: Int,
    val total: Int
)

data class Meta(
    val pagination: Pagination
)