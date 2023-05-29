package api.limc.kr.blog.domain.post

import api.limc.kr.blog.domain.post.dto.PostInfoDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/public/post"])
class PostPublicController(private val service: PostService) {
    @GetMapping(path = ["/{id}"])
    fun findById(@PathVariable(name = "id") id: Long) = service.findById(id)
    @GetMapping(path = ["/site/{name}"])
    fun findAllBySite(
        @PathVariable(name = "name") name: String,
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable
    ): Page<PostInfoDto> {
        return service.findAllBySite(name, page)
    }
    @GetMapping(path = ["/topic/{id}"])
    fun findAllByTopic(
        @PathVariable(name = "id") id: Long,
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable
    ): Page<PostInfoDto> {
        return service.findAllByTopic(id, page)
    }
    @GetMapping(path = ["/series/{id}"])
    fun findAllBySeries(
        @PathVariable(name = "id") id: Long,
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable
    ): Page<PostInfoDto> {
        return service.findAllBySeries(id, page)
    }
}