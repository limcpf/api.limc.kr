package api.limc.kr.blog.domain.post

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.post.dto.PostDetailDto
import api.limc.kr.blog.domain.post.dto.PostDto
import api.limc.kr.blog.domain.post.dto.PostInfoDto
import api.limc.kr.blog.domain.post.dto.PostListDto
import api.limc.kr.blog.domain.series.Series
import api.limc.kr.blog.domain.series.SeriesService
import api.limc.kr.blog.domain.series.dto.SeriesListDto
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.site.SiteService
import api.limc.kr.blog.domain.topic.Topic
import api.limc.kr.blog.domain.topic.TopicService
import api.limc.kr.blog.domain.util.BlogServiceFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService(private val repository: PostRepository) {
    @Autowired private lateinit var blogServiceFacade: BlogServiceFacade;

    fun save(dto: PostDto): PostInfoDto = repository.save(Post(dto)).toInfoDto()
    fun findById(id: Long): PostInfoDto = repository.findById(id).orElseThrow {
        LimcException(LimcResponseCode.NOT_FOUND)
    }.toInfoDto()

    fun findAll(page: Pageable): Page<PostListDto> = repository.findAll(page).map { PostListDto(it) }

    fun findAllBySite(siteName: String, pageable: Pageable): Page<PostInfoDto> {
        val site: Site = blogServiceFacade.getSite(siteName)

        return repository.findAllBySite(site, pageable).map { it.toInfoDto() }
    }

    fun findAllByTopic(topicId: Long, pageable: Pageable): Page<PostInfoDto> {
        val topic: Topic = blogServiceFacade.getTopic(topicId)

        return repository.findAllByTopic(topic, pageable).map { it.toInfoDto() }
    }

    fun findAllBySeries(seriesId: Long, pageable: Pageable): Page<PostInfoDto> {
        val series = blogServiceFacade.getSeries(seriesId)

        return repository.findAllBySeries(series, pageable).map { it.toInfoDto() }
    }

    fun update(postDto: PostDetailDto): PostInfoDto {
        // modify 변수 선언
        var isModify = false

        // post 불러오기
        val post:Post = getPost(postDto.id)

        // site, topic, series 수정 내용(정합성 검사 필요), site 만 수정은 불가능
        if(
            post.series?.id != postDto.series
            || post.topic.id != postDto.topic
            || post.site.name != postDto.site
        ) {
            lateinit var series: Series

            try {
                series = blogServiceFacade.getSeries(postDto.series!!)
            } catch (e: LimcException) {
                if(e.code == LimcResponseCode.NOT_FOUND.code) {
                    throw LimcException(PostResponseCode.SERIES_NOT_FOUND)
                } else {
                    throw e
                }
            }

            isModify = true

            post.series = series
            post.topic = series.topic
            post.site = series.topic.site
        } else if(post.topic.id != postDto.topic) {
            lateinit var topic: Topic

            try {
                topic = blogServiceFacade.getTopic(postDto.topic)
            } catch (e: LimcException) {
                if(e.code == LimcResponseCode.NOT_FOUND.code) {
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

        if(isModify) return repository.save(post).toInfoDto()
        else throw LimcException(PostResponseCode.NO_CHANGES_FOUND)
    }

    fun delete(id:Long) = repository.deleteById(id)

    fun getPost(id:Long?): Post {
        if (id == null) throw LimcException(LimcResponseCode.INVALID_ID_PARAMETER)
        return repository.findById(id).orElseThrow { LimcException(LimcResponseCode.NOT_FOUND) }
    }


    fun getPostCntByTopic(topic: Topic): Int
            = repository.countByTopic(topic)
    fun getPostCntBySite(site: Site): Int
        = repository.countBySite(site)

    fun getPostCntBySeries(series: Series): Int
        = repository.countBySeries(series)

}