package api.limc.kr.blog.domain.site.dto

import api.limc.kr.blog.domain.BaseTimeDto
import java.time.LocalDateTime

data class SiteDto(val name: String): BaseTimeDto() {
    constructor(name: String, createdAt:LocalDateTime?, updatedAt:LocalDateTime?): this(name) {
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}
