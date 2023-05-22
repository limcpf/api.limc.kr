package api.limc.kr.blog.domain.series

import api.limc.kr.blog.domain.series.dto.SeriesDto
import api.limc.kr.blog.domain.series.dto.SeriesListDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/series"])
class SeriesController(private val service: SeriesService) {

    @PostMapping
    fun save(@RequestBody seriesDto: SeriesDto): SeriesDto = service.save(seriesDto)

    @GetMapping
    fun findAll(
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable
    ): Page<SeriesDto> = service.findAll(page)

    @GetMapping(path = ["/topic/:id"])
   fun findAllByTopic(
       @PathVariable("id") id:Long,
       @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable
   ): Page<SeriesListDto> = service.findAllByTopic(id, page)

    @PatchMapping
    fun update(@RequestBody dto: SeriesListDto): SeriesDto = service.update(dto)

    @DeleteMapping(path = ["/:id"])
    fun delete(@PathVariable("id") id:Long) = service.delete(id)
}