package api.limc.kr.blog.domain.admin.dto

import api.limc.kr.blog.domain.admin.Admin

data class AdminDto(
    val id: String?,
    val name: String,
    val password: String
) {
    constructor(admin: Admin): this(admin.id, admin.name, admin.password)
}