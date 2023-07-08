package api.limc.kr.blog.domain.series

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.series.dto.SeriesDto
import api.limc.kr.blog.domain.series.dto.SeriesLightDto
import api.limc.kr.blog.domain.series.dto.SeriesListDto
import api.limc.kr.blog.domain.series.dto.SeriesPostDto
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.site.SiteService
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.topic.Topic
import api.limc.kr.blog.domain.topic.TopicService
import api.limc.kr.blog.domain.topic.dto.TopicDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SeriesService(private val repository: SeriesRepository) {
    @Autowired private lateinit var topicService: TopicService
    @Autowired private lateinit var siteService: SiteService

    fun save(dto: SeriesPostDto): SeriesDto {
        val siteDto = SiteDto(dto.site)
        val topicDto = TopicDto(dto.topic, dto.site, "생성용")
        val seriesDto = SeriesDto(null, topicDto, siteDto, dto.title)

        return repository.save(Series(seriesDto)).toDto()
    }

    fun findById(id: Long): SeriesDto
        = repository
            .findById(id)
            .orElseThrow{ LimcException(LimcResponseCode.NOT_FOUND) }
            .toDto()

    fun findAll(page: Pageable): Page<SeriesListDto> {
        return repository.findAll(page).map { SeriesListDto(it) }
    }

    fun findAllBySite(name: String, page: Pageable): Page<SeriesDto> {
        return repository.findAllBySite(getSite(name), page)
    }
    fun findAllByTopic(id: Long, page: Pageable): Page<SeriesLightDto>
        = repository.findAllByTopic(getTopic(id), page).map { it.toListDto() }
    private fun getSite(name: String):Site = siteService.getSite(name)
    private fun getTopic(id:Long?): Topic = topicService.getTopic(id)
    fun update(dto: SeriesLightDto): SeriesDto {
        var isModify = false

        val series = repository.findById(dto.id!!).orElseThrow{
            LimcException(SeriesResponseCode.SERIES_NOT_FOUND)
        }

        if(series.title != dto.title) {
            series.title = dto.title
            isModify = true
        }

        if(isModify) return repository.save(series).toDto()
        else throw LimcException(SeriesResponseCode.NO_CHANGES_FOUND)
    }

    fun delete(id: Long) = repository.deleteById(id)

    fun getSeries(id:Long?): Series {
        if (id == null) throw LimcException(LimcResponseCode.INVALID_ID_PARAMETER)
        return repository.findById(id).orElseThrow { LimcException(LimcResponseCode.NOT_FOUND) }
    }

    fun getSeriesByTopic(topic: Topic): Int
            = repository.countByTopic(topic)
    fun getSeriesByTopic(topics: List<Long?>): Int
            = repository.countByTopicIds(topics)


}