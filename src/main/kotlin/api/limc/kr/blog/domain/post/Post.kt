package api.limc.kr.blog.domain.post

import api.limc.kr.blog.domain.BaseTimeEntity
import api.limc.kr.blog.domain.series.Series
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.topic.Topic
import jakarta.persistence.*

@Entity
class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?,
    @ManyToOne @JoinColumn val site: Site,
    @ManyToOne @JoinColumn val topic: Topic,
    @ManyToOne @JoinColumn val series: Series?,
    @Column(length = 255) val title: String,
    @Lob val content: String
):BaseTimeEntity()