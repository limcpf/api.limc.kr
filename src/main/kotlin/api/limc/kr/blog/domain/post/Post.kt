package api.limc.kr.blog.domain.post

import api.limc.kr.blog.domain.BaseTimeEntity
import api.limc.kr.blog.domain.post.dto.PostDto
import api.limc.kr.blog.domain.post.dto.PostInfoDto
import api.limc.kr.blog.domain.series.Series
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.topic.Topic
import jakarta.persistence.*

@Entity
class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @ManyToOne @JoinColumn var site: Site,
    @ManyToOne @JoinColumn var topic: Topic,
    @ManyToOne @JoinColumn var series: Series?,
    @Column(length = 255) var title: String,
    @Column(columnDefinition="TEXT") var content: String
):BaseTimeEntity() {
    fun toDto(): PostDto = PostDto(this)
    fun toInfoDto(): PostInfoDto = PostInfoDto(this)
    constructor(dto: PostDto): this(
        dto.id,
        Site(dto.site),
        Topic(dto.topic),
        dto.series?.let { Series(it) },
        dto.title,
        dto.content
    )
}