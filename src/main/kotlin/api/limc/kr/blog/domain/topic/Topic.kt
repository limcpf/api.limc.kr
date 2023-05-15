package api.limc.kr.blog.domain.topic

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.BaseTimeEntity
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.topic.dto.TopicDto
import jakarta.persistence.*

@Entity
class Topic(id: Long?, site:Site, name:String?): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = id

    @ManyToOne
    @JoinColumn(name = "site_name")
    var site:Site = site

    @Column(length = 255, unique = true)
    var name:String = validName(name)
        set(value) { field = validName(value) }

    private fun validName(v:String?): String {
        if(v.isNullOrBlank()) throw LimcException(LimcResponseCode.INVALID_NAME_PARAMETER)
        return v
    }

    fun toDto() = TopicDto(id, site.toDto(), name)
    constructor(site:Site, name:String?): this(null, site, name)
}