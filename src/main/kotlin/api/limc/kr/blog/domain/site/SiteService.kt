package api.limc.kr.blog.domain.site

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.util.dto.SelectDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SiteService(val repository: SiteRepository) {
    fun findAll():List<SelectDto> = repository.findAll().map {it.toSelectDto()}
    fun findAll(page: Pageable): Page<SiteDto> = repository.findAll(page).map {it.toDto()}
    fun save(dto: SiteDto): SiteDto {
        val cnt: Int = repository.countByName(dto.name.uppercase())
        if(cnt > 0) throw LimcException(SiteResponseCode.DUPLICATE_NAME)
        return repository.save(Site(dto)).toDto()
    }
    fun findByName(name: String): SiteDto {
        val site:Site
            = repository.findById(name).orElseThrow { throw LimcException(LimcResponseCode.NOT_FOUND) }
        return site.toDto()
    }
    fun getSite(name:String): Site =
        repository.findById(name).orElseThrow { throw LimcException(LimcResponseCode.NOT_FOUND) }}