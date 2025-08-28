package com.suluhulabs.hrms.dto

data class ResponseBody<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)

data class PaginatedResponseBody<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null,
    val metadata: Metadata
) {
    data class Metadata(
        val page: Int,
        val size: Int,
        val query: String,
        val totalRecords: Int,
        val totalPages: Int
    )
}
