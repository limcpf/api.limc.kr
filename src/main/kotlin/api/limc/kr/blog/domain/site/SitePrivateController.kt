package api.limc.kr.blog.domain.site

import api.limc.kr.blog.domain.site.dto.SiteDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/private/site"])
class SitePrivateController(val service: SiteService) {
    @GetMapping fun findAll() = service.findAll()
    @PostMapping fun save(@RequestBody dto:SiteDto) = service.save(dto)

}