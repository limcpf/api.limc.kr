package api.limc.kr.blog.domain.site

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.LimcResponseCode
import api.limc.kr.blog.domain.BaseTimeEntity
import api.limc.kr.blog.domain.site.dto.SiteDto
import api.limc.kr.blog.domain.util.dto.SelectDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Site(name: String?) : BaseTimeEntity() {
    constructor(dto: SiteDto) : this(dto.name)

    @Id
    @Column(length = 5)
    var name: String = validName(name)
        set(value) { field = validName(value) }

    private fun validName(v: String?): String {
        if(v.isNullOrEmpty()) throw LimcException(LimcResponseCode.INVALID_NAME_PARAMETER)
        return v.uppercase()
    }

    fun toDto(): SiteDto = SiteDto(this.name, this.createdAt, this.updatedAt)
    fun toSelectDto() = SelectDto(this.name, this.name)

    override fun toString(): String {
        return """
            name : ${this.name}
            createdAt: ${this.createdAt}
            updatedAt: ${this.updatedAt}
        """.trimIndent()
    }
}