package api.limc.kr.blog.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "SITE")
class Site {
    @Id
    @Column(length = 5)
    var name: String? = null
}