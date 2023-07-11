package api.limc.kr.blog.domain.series

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.series.dto.*
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.site.SiteService
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.topic.Topic
import api.limc.kr.blog.domain.topic.TopicService
import api.limc.kr.blog.domain.topic.dto.TopicDto
import api.limc.kr.blog.domain.util.BlogServiceFacade
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SeriesService(private val repository: SeriesRepository) {
    @Autowired private lateinit var blogServiceFacade: BlogServiceFacade;

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
        val site: Site = blogServiceFacade.getSite(name)
        return repository.findAllBySite(site, page)
    }
    fun findAllByTopic(id: Long, page: Pageable): Page<SeriesLightDto> {
        val topic:Topic = blogServiceFacade.getTopic(id)
        return repository.findAllByTopic(topic, page).map { it.toListDto() }
    }
    @Transactional
    fun update(dto: SeriesPatchDto): SeriesDto {
        var isModify = false

        val series = repository.findById(dto.id).orElseThrow{
            LimcException(SeriesResponseCode.SERIES_NOT_FOUND)
        }

        if(series.title != dto.title) {
            series.title = dto.title
            isModify = true
        }

        if(series.site.name != dto.site || series.topic.id != dto.topic) {
            val topic: Topic = blogServiceFacade.getTopic(dto.topic)
            val isUpdated = blogServiceFacade.updateFKForSeries(series.id!!, topic.site.name!!, topic.id!!)
            if(!isUpdated) throw LimcException(SeriesResponseCode.INVALID_FK_UPDATE_POST)
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