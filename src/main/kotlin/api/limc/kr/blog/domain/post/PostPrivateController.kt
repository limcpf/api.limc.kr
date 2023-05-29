package api.limc.kr.blog.domain.post

import api.limc.kr.blog.domain.post.dto.PostDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/private/post"])
class PostPrivateController(private val service: PostService) {
    @PostMapping fun save(@RequestBody dto: PostDto) = service.save(dto)
    @PatchMapping fun update(@RequestBody dto: PostDto) = service.update(dto)
    @DeleteMapping(path = ["/{id}"])
    fun delete(@PathVariable(name = "id") id:Long) = service.delete(id)
}