package api.limc.kr.blog.domain.topic.dto

import api.limc.kr.blog.domain.BaseTimeDto
import api.limc.kr.blog.domain.topic.Topic

data class TopicListDto(
    val id: Long?,
    val name: String,
    val site: String
):BaseTimeDto() {
    constructor(topic: Topic): this(
        topic.id,
        topic.name,
        topic.site.name
    ) {
        super.createdAt = topic.createdAt
        super.updatedAt = topic.updatedAt
    }
}