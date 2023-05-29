package api.limc.kr.blog.domain.topic

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/public/topic"])
class TopicPublicController(val service: TopicService) {
    @GetMapping(path = ["/{id}"])
    fun findById(@PathVariable("id") id:Long) = service.findById(id)

    @GetMapping
    fun findAll(
        @RequestParam("site") site:String,
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) page:Pageable
    ) = service.findAllBySite(site, page)
}