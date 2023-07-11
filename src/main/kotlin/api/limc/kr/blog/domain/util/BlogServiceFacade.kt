package api.limc.kr.blog.domain.util

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.post.Post
import api.limc.kr.blog.domain.post.PostRepository
import api.limc.kr.blog.domain.series.Series
import api.limc.kr.blog.domain.series.SeriesRepository
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.site.SiteRepository
import api.limc.kr.blog.domain.topic.Topic
import api.limc.kr.blog.domain.topic.TopicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/*
서비스 단에서 서로의 서비스를 호출 하지 않고
필요한 메서드를 사용하기 위함
 */
@Service
class BlogServiceFacade {
    @Autowired
    private lateinit var siteRepository: SiteRepository
    @Autowired
    private lateinit var topicRepository: TopicRepository
    @Autowired
    private lateinit var seriesRepository: SeriesRepository
    @Autowired
    private lateinit var postRepository: PostRepository

    val NOT_FOUND = { throw LimcException(LimcResponseCode.NOT_FOUND) }
    fun getSite(name: String): Site = siteRepository.findById(name).orElseThrow(NOT_FOUND)

    fun getTopic(id: Long): Topic = topicRepository.findById(id).orElseThrow(NOT_FOUND)

    fun getSeries(id: Long): Series = seriesRepository.findById(id).orElseThrow(NOT_FOUND)

    fun getPost(id:Long): Post = postRepository.findById(id).orElseThrow(NOT_FOUND)

    /**
     * Series 수정 시 해당 Series 에 속하는 Post의 값들을 수정함
     *
     * @param seriesId 변경될 Series 의 pk 값
     * @param siteName 변경할 Site Entity 의 pk 값
     * @param topicId 변경할 topic Entity 의 pk 값
     * @return 수정 변경 값을 사
     */
    fun updateFKForSeries(seriesId:Long, siteName:String, topicId:Long):Boolean {
        val update = postRepository.updateFkForSeries(seriesId, siteName, topicId)
        return true
    }

}