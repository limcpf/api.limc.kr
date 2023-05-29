package api.limc.kr.blog.domain.post.dto

import api.limc.kr.blog.domain.post.Post
import api.limc.kr.blog.domain.series.dto.SeriesListDto
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.topic.dto.TopicInfoDto

data class PostInfoDto(val post: Post) {
    val id: Long? = post.id
    val site: SiteDto = post.site.toDto()
    val topic: TopicInfoDto = post.topic.toInfoDto()
    val series: SeriesListDto? = post.series?.toListDto()
    val title: String = post.title
    val content: String = post.content
}