package api.limc.kr.blog.domain.post

import api.limc.kr.blog.domain.BaseTimeEntity
import api.limc.kr.blog.domain.series.Series
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.topic.Topic
import jakarta.persistence.*

@Entity
class Post(
    id: Long?,
    site: Site,
    topic: Topic,
    series: Series?,
    title: String,
    content: String
):BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = id

    @ManyToOne
    @JoinColumn(name="site_id")
    val site:Site = site

    @ManyToOne
    @JoinColumn(name = "topic_id")
    val topic: Topic = topic

    @ManyToOne
    @JoinColumn(name = "series_id")
    val series:Series? = series

    @Column(length = 255)
    val title: String = title

    @Lob
    val content: String = content
}