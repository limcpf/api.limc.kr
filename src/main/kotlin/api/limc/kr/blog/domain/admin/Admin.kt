package api.limc.kr.blog.domain.admin

import api.limc.kr.blog.domain.BaseTimeEntity
import api.limc.kr.blog.domain.admin.dto.AdminDto
import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
class Admin(
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: String?,
    @Column(length = 30, unique = true) val name: String,
    password: String
): BaseTimeEntity() {

    @Column(length = 256) var password: String = password
        private set

    fun toDto(): AdminDto = AdminDto(this)
    fun encodePassword(): Admin {
        this.password = BCryptPasswordEncoder().encode(password)
        return this
    }
    constructor(dto:AdminDto): this(dto.id, dto.name, dto.password)
}