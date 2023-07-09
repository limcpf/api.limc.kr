package api.limc.kr.blog.domain.series.dto

import api.limc.kr.blog.domain.BaseTimeDto

data class SeriesDetailDto(
    val id: Long?,
    val site: String,
    val topic: Long?,
    val title: String?,
    val postCnt: Int?
): BaseTimeDto() {
    constructor(series: SeriesDto, postCnt: Int?): this(
        series.id,
        series.site.name,
        series.topic.id,
        series.title,
        postCnt
    ) {
        this.createdAt = series.createdAt
        this.updatedAt = series.updatedAt
    }
}
