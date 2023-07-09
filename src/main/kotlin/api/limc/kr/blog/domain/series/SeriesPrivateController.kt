package api.limc.kr.blog.domain.series

import api.limc.kr.blog.domain.series.dto.*
import api.limc.kr.blog.domain.util.BlogFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/private/series"])
class SeriesPrivateController(private val service: SeriesService) {
    @Autowired private lateinit var blogFacade: BlogFacade
    @GetMapping
    fun findAll(
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable
    ): Page<SeriesListDto> = service.findAll(page)
    @GetMapping(path = ["/{id}"])
    fun findById(@PathVariable(value = "id") id:Long):SeriesDetailDto {
        val seriesDto:SeriesDto = service.findById(id)
        return SeriesDetailDto(seriesDto, blogFacade.getCntObj(seriesDto))
    }
    @PostMapping
    fun save(@RequestBody seriesDto: SeriesPostDto): SeriesDto = service.save(seriesDto)

    @PatchMapping
    fun update(@RequestBody dto: SeriesPatchDto): SeriesDto = service.update(dto)

    @DeleteMapping(path = ["/{id}"])
    fun delete(@PathVariable("id") id:Long) = service.delete(id)
}