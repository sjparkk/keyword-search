package com.keyword.keywordsearch.domain

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Schema(title = "블로그 키워드")
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
@Table(name = "blog_keyword")
class BlogKeyword (

    @field:Schema(title = "블로그 검색 키워드")
    var keyword: String? = null,

    @field:Schema(title = "등록일시")
    @CreatedDate
    var createdAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Schema(title = "블로그 키워드 ID")
    val id: Long? = null

) {
    override fun toString(): String {
        return "BlogKeyword(keyword=$keyword, createdAt=$createdAt, id=$id)"
    }
}