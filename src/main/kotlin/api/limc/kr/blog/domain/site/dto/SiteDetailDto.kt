package api.limc.kr.blog.domain.site.dto

import api.limc.kr.blog.domain.BaseTimeDto

data class SiteDetailDto(
    val siteDto: SiteDto,
    val topicCnt: Int,
    val seriesCnt: Int,
    val postCnt: Int
):BaseTimeDto(siteDto.createdAt, siteDto.updatedAt) {
    val name: String = siteDto.name
}