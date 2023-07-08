package api.limc.kr.blog.domain.topic

import api.limc.kr.blog.domain.topic.dto.TopicDetailDto
import api.limc.kr.blog.domain.topic.dto.TopicDto
import api.limc.kr.blog.domain.util.BlogFacade
import api.limc.kr.blog.domain.util.dto.SelectDto
import api.limc.kr.blog.shared.enums.Domain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/private/topic"])
class TopicPrivateController(val service: TopicService) {
    @Autowired lateinit var blogFacade: BlogFacade

    @GetMapping(path = ["/list/{name}"])
    fun findAllForSelect(@PathVariable("name") name:String):List<SelectDto> = service.findAll(name);
    @GetMapping
    fun findAll(
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable
    ) = service.findAll(page)

    @GetMapping(path = ["/{id}"])
    fun findById(@PathVariable("id") id:Long): TopicDetailDto {
        val topic: TopicDto = service.findById(id)

        val intMap:Map<Domain,Int> = blogFacade.getCntObj(topic);
        return TopicDetailDto(topic, intMap[Domain.SERIES], intMap[Domain.POST])
    }

    @PostMapping fun save(@RequestBody dto: TopicDto) = service.save(dto)
    @PatchMapping fun update(@RequestBody dto: TopicDto) = service.update(dto)
    @DeleteMapping(path = ["/{id}"]) fun delete(@PathVariable(value = "id") id:Long) = service.delete(id)
}