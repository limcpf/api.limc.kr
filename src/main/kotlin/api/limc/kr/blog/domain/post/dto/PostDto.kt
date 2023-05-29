package api.limc.kr.blog.domain.post.dto

import api.limc.kr.blog.domain.BaseTimeDto
import api.limc.kr.blog.domain.post.Post
import api.limc.kr.blog.domain.series.dto.SeriesDto
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.topic.Topic
import api.limc.kr.blog.domain.topic.dto.TopicDto
import jdk.internal.org.jline.utils.Colors.s

data class PostDto(
    val id: Long?,
    val site: SiteDto,
    val topic: TopicDto,
    val seriesDto: SeriesDto?,
    val title: String,
    val content: String
    ): BaseTimeDto() {
        constructor(post: Post): this(
            post.id,
            post.site.toDto(),
            post.topic.toDto(),
            post.series?.toDto(),
            post.title,
            post.content
        ) {
            this.createdAt = post.createdAt
            this.updatedAt = post.updatedAt
        }
    }