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
    @Autowired
    lateinit var siteService: SiteService
    fun save(dto: TopicDto): TopicDto = repository.save(Topic(dto)).toDto()

    fun findById(id: Long): TopicDto = getTopic(id).toDto()
    fun findAllBySite(site: String, page: Pageable): Page<TopicDto> {
        val topics = repository.findAllBySite(getSite(site), page)

        return topics.map { it.toDto() }
    }

    fun update(dto: TopicDto): TopicDto {
        var isModify = false // 수정 여부 판단
        val topic = getTopic(dto.id) // 수정할 topic

        if (!dto.name.isNullOrBlank() && topic.name != dto.name) { // dto 의 값이 존재하는데 기존 topic 이름과 다를 경우
            topic.name = dto.name
            isModify = true // 수정 여부가 있다고 판단한다.
        }

        // 해당 경우는 빈도수가 낮을것으로 추측함
        if (topic.site.name != dto.site.name) { // site name 이 변경되는 경우
            val site: Site
            try {
                /**
                 * dto 의 site 이름으로 site domain 을 조회한다.
                 * 없는 site 인 경우 SiteService 에서 NOT_FOUND 를 throw 해주나, 해당 Exception 을 받아
                 * 해당 서비스에서 다시 throw 하여 response 해준다.
                 */
                site = getSite(dto.site.name)

                topic.site = site
                isModify = true // 수정 여부가 있다고 판단한다.
            } catch (e: LimcException) {
                throw LimcException(TopicResponseCode.TOPIC_SITE_NOT_FOUND)
            }
        }

        if (isModify) return repository.save(topic).toDto()
        else throw LimcException(TopicResponseCode.NO_CHANGES_FOUND) // 변경 건이 없는경우 BadRequest 를 던져준다.
    }

    fun delete(id: Long) = repository.deleteById(id)

    private fun getSite(name: String): Site = siteService.findByName(name)

    fun getTopic(id: Long?): Topic {
        if (id == null) throw LimcException(LimcResponseCode.INVALID_ID_PARAMETER)
        return repository.findById(id).orElseThrow { LimcException(LimcResponseCode.NOT_FOUND) }
    }
}