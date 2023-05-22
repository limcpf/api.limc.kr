package api.limc.kr.blog.domain.topic

import api.limc.kr.blog.domain.topic.dto.TopicDto
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/topic"])
class TopicController(val service: TopicService) {
    @PostMapping
    fun save(@RequestBody dto: TopicDto) = service.save(dto)

    @GetMapping(path = ["/{id}"])
    fun findById(@PathVariable("id") id:Long) = service.findById(id)

    @GetMapping
    fun findAll(
        @RequestParam("site") site:String,
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) page:Pageable
    ) = service.findAllBySite(site, page)

    @PatchMapping
    fun update(@RequestBody dto: TopicDto) = service.update(dto)

    @DeleteMapping(path = ["/{id}"])
    fun delete(@PathVariable(value = "id") id:Long) = service.delete(id)
}