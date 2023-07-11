package api.limc.kr.blog.domain.site

import api.limc.kr.blog.domain.site.dto.SiteDetailDto
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.util.BlogControllerFacade
import api.limc.kr.blog.domain.util.dto.SelectDto
import api.limc.kr.blog.shared.enums.Domain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/private/site"])
class SitePrivateController(val service: SiteService) {
    @Autowired lateinit var blogControllerFacade: BlogControllerFacade
    @GetMapping(path = ["/list"])
    fun findAllForSelect():List<SelectDto> = service.findAll()

    @GetMapping fun findAll(
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) page: Pageable
    ) = service.findAll(page)

    @PostMapping fun save(@RequestBody dto:SiteDto) = service.save(dto)

    @GetMapping(path = ["/{name}"])
    fun findByName(@PathVariable(name = "name") name: String): SiteDetailDto {
        val site = service.findByName(name)
        val cntMap = blogControllerFacade.getCntObj(site)

        return SiteDetailDto(site, cntMap[Domain.TOPIC], cntMap[Domain.SERIES], cntMap[Domain.POST])
    }
}