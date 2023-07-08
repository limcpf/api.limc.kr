package api.limc.kr.blog.domain.series.dto

import api.limc.kr.blog.domain.BaseTimeDto
import api.limc.kr.blog.domain.series.Series

data class SeriesListDto(
    val id: Long?,
    val site: String,
    val topic: Long?,
    val topicName: String,
    val title: String
): BaseTimeDto() {
    constructor(series:Series): this(series.id, series.site.name, series.topic.id, series.topic.name, series.title) {
        super.createdAt = series.createdAt
        super.updatedAt = series.updatedAt
    }
}