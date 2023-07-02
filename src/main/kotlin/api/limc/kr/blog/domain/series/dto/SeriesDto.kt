package api.limc.kr.blog.domain.series.dto

import api.limc.kr.blog.domain.BaseTimeDto
import api.limc.kr.blog.domain.series.Series
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.topic.dto.TopicDto

data class SeriesDto(val id:Long?, val topic:TopicDto, val site:SiteDto,val title:String) :BaseTimeDto() {
    constructor(series: Series): this(series.id, series.topic.toDto(), series.site.toDto(), series.title) {
        this.createdAt = series.createdAt
        this.updatedAt = series.updatedAt
    }
}
