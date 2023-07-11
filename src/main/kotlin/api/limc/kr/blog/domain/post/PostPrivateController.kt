package api.limc.kr.blog.domain.post

import api.limc.kr.blog.domain.post.dto.PostDetailDto
import api.limc.kr.blog.domain.post.dto.PostListDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/private/post"])
class PostPrivateController(private val service: PostService) {
    @GetMapping
    fun findAll(
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable
    ): Page<PostListDto> {
        return service.findAll(page)
    }
    @PostMapping fun save(@RequestBody dto: PostDetailDto) = service.save(dto)
    @PatchMapping fun update(@RequestBody dto: PostDetailDto) = service.update(dto)
    @DeleteMapping(path = ["/{id}"])
    fun delete(@PathVariable(name = "id") id:Long) = service.delete(id)
}