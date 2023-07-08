package api.limc.kr.blog.domain.topic.dto

import api.limc.kr.blog.domain.BaseTimeDto

data class TopicDetailDto(
    val id: Long?,
    val site: String,
    val name: String?,
    val seriesCnt: Int?,
    val postCnt: Int?
): BaseTimeDto() {
    constructor(topic:TopicDto, seriesCnt: Int?, postCnt: Int?): this(
        topic.id,
        topic.site,
        topic.name,
        seriesCnt,
        postCnt
    ) {
        this.createdAt = topic.createdAt
        this.updatedAt = topic.updatedAt
    }
}
