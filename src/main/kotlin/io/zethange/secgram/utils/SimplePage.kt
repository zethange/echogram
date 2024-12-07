package io.zethange.secgram.utils

import org.springframework.data.domain.Page

class PageDto<T>(
    val content: List<T>,

    val pageNumber: Int,
    val pageSize: Int,
    val isLastPage: Boolean,
    val totalPages: Int,
    val totalElements: Long,
)

fun <T> Page<T>.toDto(): PageDto<T> = PageDto(
    content = this.content,
    pageNumber = this.pageable.pageNumber,
    pageSize = this.pageable.pageSize,
    isLastPage = this.isLast,
    totalPages = this.totalPages,
    totalElements = this.totalElements,
)