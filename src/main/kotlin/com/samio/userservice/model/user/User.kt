package com.samio.userservice.model.user

import com.samio.core.application.validation.password.PasswordCrypto
import com.samio.core.application.validation.password.PasswordValidator
import com.samio.core.application.validation.password.ValidPassword
import com.samio.core.application.validation.userrole.UserRole
import com.samio.core.model.abstraction.AugmentableBaseEntity
import com.samio.userservice.model.userinfo.UserInfo
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.*


@Entity
@Suppress("unused")
@Table(name = "users",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["username"])
    ]
)
@Schema(description = "Represents a user entity.")
open class User(
    @field:NotBlank(message = "Username is mandatory")
    @field:Size(max = 50, message = "Username must be less than 50 characters")
    @Schema(description = "The username of the user.", example = "johndoe")
    open var username: String = "",

    @ValidPassword
    @NotBlank(message = "Password is mandatory")
    @Schema(description = "The password of the user. Stored securely.", example = "$2a$10FwKtcPAK7Yu4FSyfQzrH4OG.VViSWggg64ocuibcbPacV2RL7WnYq")
    private var _password: String = "",

    @Enumerated(EnumType.ORDINAL)
    @Schema(description = "The role of the user.", example = "ADMIN")
    open var userRole: UserRole = UserRole.GUEST,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_permissions",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")]
    )
    @Column(name = "permission_id", nullable = false)
    @Schema(description = "List of permissions assigned to the user.")
    open var permissions: List<UUID> = listOf(),

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_info_id", referencedColumnName = "id")
    @Schema(description = "Additional information about the user.")
    open var userInfo: UserInfo? = null,

    @Schema(description = "The timestamp of the user's last activity.", example = "2023-01-01T10:00:00Z")
    open var lastActive: LocalDateTime = LocalDateTime.now()
    ) : AugmentableBaseEntity() {

    open var password: String
        get() = _password
        set(value) {
            if (!PasswordValidator.isValid(value, null)) {
                throw IllegalArgumentException("Password does not meet the requirements")
            }
            _password = PasswordCrypto.hashPassword(value)
        }

}
