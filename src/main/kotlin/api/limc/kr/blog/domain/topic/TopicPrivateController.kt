package api.limc.kr.blog.domain.topic

import api.limc.kr.blog.domain.topic.dto.TopicDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/private/topic"])
class TopicPrivateController(val service: TopicService) {
    @PostMapping fun save(@RequestBody dto: TopicDto) = service.save(dto)
    @PatchMapping fun update(@RequestBody dto: TopicDto) = service.update(dto)
    @DeleteMapping(path = ["/{id}"]) fun delete(@PathVariable(value = "id") id:Long) = service.delete(id)
}