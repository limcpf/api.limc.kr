package api.limc.kr.blog.domain.series

import api.limc.kr.blog.domain.series.dto.SeriesDto
import api.limc.kr.blog.domain.series.dto.SeriesLightDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/public/series"])
class SeriesPublicController(private val service: SeriesService) {
    @GetMapping
    fun findAllBySite(
        @PathVariable(value = "name") name:String,
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable
    ): Page<SeriesDto> = service.findAllBySite(name, page)

    @GetMapping(path = ["/topic/{id}"])
   fun findAllByTopic(
       @PathVariable(value = "id") id:Long,
       @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable
   ): Page<SeriesLightDto> = service.findAllByTopic(id, page)

    @GetMapping(path = ["/{id}"])
    fun findById(@PathVariable(value = "id") id:Long) = service.findById(id)
}