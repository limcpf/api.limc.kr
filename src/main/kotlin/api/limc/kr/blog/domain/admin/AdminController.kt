package api.limc.kr.blog.domain.admin

import api.limc.kr.blog.config.auth.JwtTokenService
import api.limc.kr.blog.domain.admin.dto.AdminDto
import api.limc.kr.blog.domain.admin.dto.LoginDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminController(private val service:AdminService) {
    @Autowired private lateinit var jwtTokenService:JwtTokenService
    @PostMapping(path = ["/public/login"])
    fun login(@RequestBody adminDto: AdminDto):ResponseEntity<LoginDto> {
        val loginDto:LoginDto = jwtTokenService.getLoginResponse(adminDto)
        return ResponseEntity.ok(loginDto)
    }

    @GetMapping(path = ["/private/logout"])
    fun logout(@RequestBody adminDto: AdminDto):ResponseEntity<LoginDto> {
        val loginDto = jwtTokenService.logout(adminDto.name)
        return ResponseEntity.ok(loginDto)
    }

    @PostMapping(path = ["/public/admin"])
    fun save(@RequestBody adminDto: AdminDto):AdminDto = service.save(adminDto)

    fun deleteAllForTest():Unit = service.deleteAllForTest()
}