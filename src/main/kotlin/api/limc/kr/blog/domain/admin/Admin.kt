package api.limc.kr.blog.domain.admin

import api.limc.kr.blog.domain.BaseTimeEntity
import api.limc.kr.blog.domain.admin.dto.AdminDto
import jakarta.persistence.*

@Entity
class Admin(
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: String?,
    @Column(length = 30, unique = true) val name: String,
    @Column(length = 256) val password: String
): BaseTimeEntity() {
    fun toDto(): AdminDto = AdminDto(this)
    constructor(dto:AdminDto): this(dto.id, dto.name, dto.password)
}