package api.limc.kr.blog.domain.site.dto

import api.limc.kr.blog.domain.BaseTimeDto

data class SiteDetailDto(
    val name:String,
    val topicCnt: Int? = 0,
    val seriesCnt: Int? = 0,
    val postCnt: Int? = 0
):BaseTimeDto() {

    constructor(siteDto: SiteDto, topicCnt: Int?, seriesCnt: Int?, postCnt: Int?): this(siteDto.name, topicCnt,seriesCnt,postCnt) {
        this.createdAt = siteDto.createdAt
        this.updatedAt = siteDto.updatedAt
    }
}