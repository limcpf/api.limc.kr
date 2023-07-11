package api.limc.kr.blog.domain.post.dto

import api.limc.kr.blog.domain.BaseTimeDto
import api.limc.kr.blog.domain.post.Post

data class PostDetailDto(
    val id: Long,
    val title: String,
    val content: String,
    val site: String,
    val topic: Long,
    var series: Long?
): BaseTimeDto() {
    constructor(post:Post): this(post.id, post.title, post.content, post.site.name, post.topic.id!!, null) {
        if(post.series != null && post.series!!.id != null) {
            this.series = post.series!!.id
        }
        this.createdAt = post.createdAt
        this.updatedAt = post.updatedAt
    }
}
