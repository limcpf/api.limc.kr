package api.limc.kr.blog.domain.topic.dto

import api.limc.kr.blog.domain.site.dto.SiteDto

data class TopicDto(val id: Long?, val site:SiteDto, val name: String?) {
    constructor(site: SiteDto, name: String?):this(null, site, name)
}
