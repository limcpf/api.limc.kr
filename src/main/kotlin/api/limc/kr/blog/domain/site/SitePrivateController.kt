package api.limc.kr.blog.domain.site

import api.limc.kr.blog.domain.site.dto.SiteDetailDto
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.util.BlogFacade
import api.limc.kr.blog.shared.enums.Domain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/private/site"])
class SitePrivateController(val service: SiteService) {
    @Autowired lateinit var blogFacade: BlogFacade

    @GetMapping fun findAll(
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) page: Pageable
    ) = service.findAll(page)

    @PostMapping fun save(@RequestBody dto:SiteDto) = service.save(dto)

    @GetMapping(path = ["/{name}"])
    fun findByName(@PathVariable(name = "name") name: String): SiteDetailDto {
        val site = service.getSite(name)
        val cntMap = blogFacade.getCntObjInSite(site)

        return SiteDetailDto(site.toDto(), cntMap[Domain.TOPIC], cntMap[Domain.SERIES], cntMap[Domain.POST])
    }
}