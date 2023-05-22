package api.limc.kr.blog.domain.series.dto

import api.limc.kr.blog.domain.BaseTimeDto
import api.limc.kr.blog.domain.series.Series

data class SeriesListDto(val series: Series):BaseTimeDto(series) {
    val id:Long? = series.id
    val title:String = series.title
}