package com.suluhulabs.hrms.model

import jakarta.persistence.*

@Entity(name = "organization_member")
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
    var role: Role = Role.MEMBER,

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'"
    )
    var status: Status = Status.ACTIVE

) : BaseEntity() {
    enum class Role { ADMIN, MEMBER }
    enum class Status { ACTIVE, INACTIVE }
}
