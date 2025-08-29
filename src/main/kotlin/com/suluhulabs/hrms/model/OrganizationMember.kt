package com.suluhulabs.hrms.model

import jakarta.persistence.*

@Entity(name = "organization_members")
@Table(
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "organization_id"])]
)
data class OrganizationMember(
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    val organization: Organization,

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "ENUM('ADMIN', 'MEMBER') DEFAULT 'MEMBER'"
    )
    var role: MemberRole = MemberRole.MEMBER
) : BaseEntity() {
    enum class MemberRole { ADMIN, MEMBER }
}
