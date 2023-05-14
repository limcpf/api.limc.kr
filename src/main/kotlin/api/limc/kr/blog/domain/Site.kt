package api.limc.kr.blog.domain

import api.limc.kr.blog.config.exception.LimcException
import api.limc.kr.blog.config.exception.enums.SiteResponseCode
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "Site")
class Site(name: String?) : BaseTimeEntity() {
    @Id
    @Column(length = 5)
    var name: String? = validId(name)
        set(value) { field = validId(value) }

    private fun validId(v: String?): String {
        if(v.isNullOrEmpty()) throw LimcException(SiteResponseCode.INVALID_NAME_PARAMETER)
        return v.uppercase()
    }

    override fun toString(): String {
        return """
            name : ${this.name}
            createdAt: ${this.createdAt}
            updatedAt: ${this.updatedAt}
        """.trimIndent()
    }
}