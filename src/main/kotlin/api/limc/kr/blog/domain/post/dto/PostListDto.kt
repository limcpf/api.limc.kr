package api.limc.kr.blog.domain.post.dto

import api.limc.kr.blog.domain.BaseTimeDto
import api.limc.kr.blog.domain.post.Post

data class PostListDto(
    val id: Long,
    val title: String,
    val site: String,
    val topic: Long,
    val topicName: String,
    var series: Long?,
    var seriesName: String?
): BaseTimeDto() {
    constructor(post:Post): this(post.id, post.title,post.site.name, post.topic.id, post.topic.name, null, null) {
        if(post.series != null && post.series!!.id != null) {
            this.series = post.series!!.id
            this.seriesName = post.series!!.title
        }
        this.createdAt = post.createdAt
        this.updatedAt = post.updatedAt
    }
}
