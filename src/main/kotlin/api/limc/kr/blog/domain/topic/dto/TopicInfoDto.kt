package api.limc.kr.blog.domain.topic.dto

import api.limc.kr.blog.domain.topic.Topic

data class TopicInfoDto(val id: Long?, val name: String) {
    constructor(topic: Topic): this(topic.id, topic.name)
}