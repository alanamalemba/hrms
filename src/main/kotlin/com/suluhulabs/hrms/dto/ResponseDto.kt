package com.suluhulabs.hrms.dto

data class ResponseBodyDto<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)

data class PaginatedResponseBodyDto<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null,
    val metadata: Metadata
) {
    data class Metadata(
        val page: Int,
        val size: Int,
        val query: String,
        val totalElements: Int,
        val totalPages: Int
    )
}
