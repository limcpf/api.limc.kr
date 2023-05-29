package api.limc.kr.blog.domain.admin.dto

data class LoginDto(
    val name: String,
    val accessToken: String,
    val refreshToken: String,
    val role: String
)