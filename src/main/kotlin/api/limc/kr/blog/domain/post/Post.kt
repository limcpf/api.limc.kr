package api.limc.kr.blog.domain.post

import api.limc.kr.blog.domain.BaseTimeEntity
import api.limc.kr.blog.domain.post.dto.PostDto
import api.limc.kr.blog.domain.series.Series
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.topic.Topic
import jakarta.persistence.*

@Entity
class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    site: Site,
    topic: Topic,
    series: Series?,
    @Column(length = 255) var title: String,
    @Lob var content: String
):BaseTimeEntity() {

    @ManyToOne
    @JoinColumn
    var site: Site = site

    @ManyToOne
    @JoinColumn
    var topic: Topic = topic

    @ManyToOne
    @JoinColumn
    var series: Series? = series

    fun toDto(): PostDto = PostDto(this)
    constructor(dto: PostDto): this(
        dto.id,
        Site(dto.site),
        Topic(dto.topic),
        dto.seriesDto?.let { Series(it) },
        dto.title,
        dto.content
    )

}