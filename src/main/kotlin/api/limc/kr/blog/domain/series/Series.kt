package api.limc.kr.blog.domain.series

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.BaseTimeEntity
import api.limc.kr.blog.domain.series.dto.SeriesDto
import api.limc.kr.blog.domain.series.dto.SeriesLightDto
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.topic.Topic
import jakarta.persistence.*

@Entity
class Series(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id:Long?,
    @ManyToOne @JoinColumn val site: Site,
    @ManyToOne @JoinColumn val topic: Topic,
    title:String
):BaseTimeEntity() {
    @Column(length = 255, unique = true)
    var title: String = validTitle(title)
        set(value) { field = validTitle(value) }

    private fun validTitle(v: String?): String {
        if(v.isNullOrBlank()) throw LimcException(LimcResponseCode.INVALID_TITLE_PARAMETER)
        return v
    }

    fun toDto():SeriesDto = SeriesDto(this)
    fun toListDto():SeriesLightDto = SeriesLightDto(this)

    constructor(dto: SeriesDto): this(dto.id,Site(dto.site),Topic(dto.topic),  dto.title)
}