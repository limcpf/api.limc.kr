package api.limc.kr.blog.domain.series

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.series.dto.SeriesDto
import api.limc.kr.blog.domain.series.dto.SeriesListDto
import api.limc.kr.blog.domain.topic.Topic
import api.limc.kr.blog.domain.topic.TopicService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SeriesService(private val repository: SeriesRepository) {
    @Autowired private lateinit var topicService: TopicService

    fun save(dto: SeriesDto): SeriesDto = repository.save(Series(dto)).toDto()

    fun findById(id: Long): SeriesDto
        = repository
            .findById(id)
            .orElseThrow{ LimcException(LimcResponseCode.NOT_FOUND) }
            .toDto()

    fun findAll(page: Pageable): Page<SeriesDto> = repository.findAll(page).map { it.toDto() }

    fun findAllByTopic(id: Long, page: Pageable): Page<SeriesListDto>
        = repository.findAllByTopic(getTopic(id), page).map { it.toListDto() }

    private fun getTopic(id:Long?): Topic = topicService.getTopic(id)
    fun update(dto: SeriesListDto): SeriesDto {
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

}