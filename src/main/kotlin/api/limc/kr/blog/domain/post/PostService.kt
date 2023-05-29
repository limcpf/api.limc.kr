package api.limc.kr.blog.domain.post

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.post.dto.PostDto
import api.limc.kr.blog.domain.series.Series
import api.limc.kr.blog.domain.series.SeriesService
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.site.SiteService
import api.limc.kr.blog.domain.topic.Topic
import api.limc.kr.blog.domain.topic.TopicService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService(private val repository: PostRepository) {
    @Autowired private lateinit var siteService:SiteService
    @Autowired private lateinit var topicService: TopicService
    @Autowired private lateinit var seriesService: SeriesService

    fun save(dto: PostDto): PostDto = repository.save(Post(dto)).toDto()
    fun findById(id: Long): PostDto = repository.findById(id).orElseThrow {
        LimcException(LimcResponseCode.NOT_FOUND)
    }.toDto()

    fun findAllBySite(siteName: String, pageable: Pageable): Page<Post> {
        val site: Site = siteService.findByName(siteName)

        return repository.findAllBySite(site, pageable)
    }

    fun findAllByTopic(topicId: Long, pageable: Pageable): Page<Post> {
        val topic: Topic = topicService.getTopic(topicId)

        return repository.findAllByTopic(topic, pageable)
    }

    fun findAllBySeries(seriesId: Long, pageable: Pageable): Page<Post> {
        val series: Series = seriesService.getSeries(seriesId)

        return repository.findAllBySeries(series, pageable)
    }

    fun update(postDto: PostDto): PostDto {
        // modify 변수 선언
        var isModify = false

        // post 불러오기
        val post:Post = getPost(postDto.id)

        // site, topic, series 수정 내용(정합성 검사 필요), site 만 수정은 불가능
        if(post.series?.id != postDto.seriesDto?.id) {
            lateinit var series: Series

            try {
                series = seriesService.getSeries(postDto.seriesDto?.id)
            } catch (e: LimcException) {
                if(e.code.equals(LimcResponseCode.NOT_FOUND)) {
                    throw LimcException(PostResponseCode.SERIES_NOT_FOUND)
                } else {
                    throw e
                }
            }

            isModify = true

            post.series = series
            post.topic = series.topic
            post.site = series.topic.site
        } else if(post.topic.id != postDto.topic.id) {
            lateinit var topic: Topic

            try {
                topic = topicService.getTopic(postDto.topic.id)
            } catch (e: LimcException) {
                if(e.code.equals(LimcResponseCode.NOT_FOUND)) {
                    throw LimcException(PostResponseCode.TOPIC_NOT_FOUND)
                } else {
                    throw e
                }
            }

            isModify = true

            post.topic = topic
            post.site = topic.site
        }

        // post 제목 수정
        if(post.title != postDto.title) {
            isModify = true
            post.title = postDto.title
        }

        // post 내용 수정
        if(post.content != postDto.content) {
            isModify = true
            post.content = postDto.content
        }

        if(isModify) return repository.save(post).toDto()
        else throw LimcException(PostResponseCode.NO_CHANGES_FOUND)
    }

    fun delete(id:Long) = repository.deleteById(id)

    fun getPost(id:Long?): Post {
        if (id == null) throw LimcException(LimcResponseCode.INVALID_ID_PARAMETER)
        return repository.findById(id).orElseThrow { LimcException(LimcResponseCode.NOT_FOUND) }
    }
}