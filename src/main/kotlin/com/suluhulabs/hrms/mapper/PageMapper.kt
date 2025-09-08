package com.suluhulabs.hrms.mapper

import com.suluhulabs.hrms.dto.PaginatedResponseBodyDto
import org.springframework.data.domain.Page

fun <T, R> Page<T>.toPaginatedResponseBodyDto(
    success: Boolean = true,
    message: String,
    query: String = "",
    transform: (T) -> R
): PaginatedResponseBodyDto<List<R>> = PaginatedResponseBodyDto(
    success,
    message,
    data = this.content.map(transform),
    metadata = PaginatedResponseBodyDto.Metadata(
        size = this.size,
        query = query,
        totalElements = this.totalElements.toInt(),
        totalPages = this.totalPages.toInt(),
        page = this.number
    )
)