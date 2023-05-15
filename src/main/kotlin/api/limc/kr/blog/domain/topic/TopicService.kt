package api.limc.kr.blog.domain.topic

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.site.SiteService
import api.limc.kr.blog.domain.topic.dto.TopicDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TopicService(val repository: TopicRepository) {
    @Autowired lateinit var siteService:SiteService
    fun save(dto: TopicDto): TopicDto
        = repository.save(
            Topic(getSite(dto.site.name), dto.name)
        ).toDto()

    fun findById(id: Long): TopicDto
        = repository.findById(id)
                .orElseThrow { LimcException(LimcResponseCode.NOT_FOUND) }
                .toDto()

    fun findAllBySite(site: String, page: Pageable): Page<TopicDto> {
        val topics = repository.findAllBySite(getSite(site), page)

        return topics.map { it.toDto() }
    }

    private fun getSite(name:String): Site = siteService.findById(name)
}