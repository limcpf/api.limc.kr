package api.limc.kr.blog.domain.series.dto

data class SeriesPatchDto(
    val id: Long,
    val site: String,
    val topic: Long,
    val title: String
)