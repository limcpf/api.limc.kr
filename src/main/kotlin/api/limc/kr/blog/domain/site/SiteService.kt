package api.limc.kr.blog.domain.site

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.site.dto.SiteDto
import org.springframework.stereotype.Service

@Service
class SiteService(val repository: SiteRepository) {
    fun findAll(): List<SiteDto> = repository.findAll().map {it.toDto()}
    fun save(dto: SiteDto): SiteDto = repository.save(Site(dto.name)).toDto()
    fun findById(name: String): Site = repository.findById(name).orElseThrow { throw LimcException(LimcResponseCode.NOT_FOUND) }
}