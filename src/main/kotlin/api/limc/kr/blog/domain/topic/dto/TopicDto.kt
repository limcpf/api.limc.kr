package api.limc.kr.blog.domain.topic.dto

import api.limc.kr.blog.domain.BaseTimeDto
import api.limc.kr.blog.domain.topic.Topic

data class TopicDto(val id: Long?, val site:String, val name: String?):BaseTimeDto() {
    constructor(topic: Topic):this(topic.id, topic.site.name, topic.name) {
        this.createdAt = topic.createdAt
        this.updatedAt = topic.updatedAt
    }
    private constructor():this(null, "", "")
}
