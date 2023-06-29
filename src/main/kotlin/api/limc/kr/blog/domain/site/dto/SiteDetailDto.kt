package api.limc.kr.blog.domain.site.dto

data class SiteDetailDto(
    val site: SiteDto,
    val topicCnt: Int? = 0,
    val seriesCnt: Int? = 0,
    val postCnt: Int? = 0
)