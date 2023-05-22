package api.limc.kr.blog.domain

import java.time.LocalDateTime

abstract class BaseTimeDto(var createdAt: LocalDateTime?, var updatedAt:LocalDateTime?) {
    constructor(entity: BaseTimeEntity): this(entity.createdAt, entity.updatedAt)
    constructor(): this(null, null)
}
