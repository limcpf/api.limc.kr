package api.limc.kr.blog.domain.util

import api.limc.kr.blog.domain.post.PostService
import api.limc.kr.blog.domain.series.SeriesService
import api.limc.kr.blog.domain.site.Site
import api.limc.kr.blog.domain.topic.Topic
import api.limc.kr.blog.domain.topic.TopicService
import api.limc.kr.blog.shared.enums.Domain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class BlogFacade {
    @Autowired lateinit var topicService: TopicService
    @Autowired lateinit var seriesService: SeriesService
    @Autowired lateinit var postService: PostService

    fun getCntObjInSite(site: Site) : Map<Domain, Int> {
        val intMap:MutableMap<Domain, Int> = EnumMap(Domain::class.java)

        // Series 때문에 Topic 전체를 들고옴(추후 Series 도메인에 site 를 넣음으로써 해결해야함)
        val topics:List<Topic> = topicService.getTopicsBySite(site)

        val seriesCnt:Int = seriesService.getSeriesByTopic(topics.map { it.id })

        val postCnt:Int = postService.getPostCntBySite(site)

        intMap[Domain.TOPIC] = topics.size
        intMap[Domain.SERIES] = seriesCnt
        intMap[Domain.POST] = postCnt

        return intMap
    }
}