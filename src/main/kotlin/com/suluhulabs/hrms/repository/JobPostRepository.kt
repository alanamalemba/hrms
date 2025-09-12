package com.suluhulabs.hrms.repository

import com.suluhulabs.hrms.model.JobPost
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JobPostingRepository : JpaRepository<JobPost, Long> {

    @Query("""
        SELECT jp
        FROM job_posts jp
        WHERE (:query IS NULL OR 
              LOWER(jp.title) LIKE LOWER(CONCAT('%', :query, '%'))
              OR LOWER(jp.description) LIKE LOWER(CONCAT('%', :query, '%')))
        AND (:organizationId IS NULL OR jp.organization.id = :organizationId)
    """)
    fun findJobPosts(
        @Param("query") query: String?,
        @Param("organizationId") organizationId: Long?,
        pageable: Pageable
    ): Page<JobPost>
}
